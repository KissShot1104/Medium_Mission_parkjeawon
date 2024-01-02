package com.mysite.medium.article.repository;


import static com.mysite.medium.article.entity.QArticle.article;
import static com.mysite.medium.article_vote.entity.QArticleVote.articleVote;
import static com.mysite.medium.comment.entity.QComment.comment;
import static com.querydsl.core.types.ExpressionUtils.count;

import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.dto.QArticleDto;
import com.mysite.medium.user.dto.QSiteUserDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;


public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public ArticleRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);

    }


//    @Override
//    public Page<ArticleDto> findAllByKeyword(String kw, Pageable pageable) {
//
//        List<ArticleDto> articleList = queryFactory
//                .select(new QArticleDto(
//                        article.id,
//                        article.subject,
//                        article.content,
//                        new QSiteUserDto(
//                                article.author.username,
//                                article.author.isPaid),
//                        article.createDate,
//                        comment.count()
//                ))
//                .from(article)
//                .leftJoin(comment).on(comment.article.eq(article))
//                .groupBy(article.id)
//                .where(
//                        articleTitleContains(kw)
//                                .or(articleContentContains(kw))
//                                .or(articleAuthorContains(kw))
//                                .or(commentContentContains(kw))
//                                .or(commentAuthorContains(kw))
//                )
//                .distinct()
//                .orderBy(article.createDate.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        long total = queryFactory
//                .selectFrom(article)
//                .leftJoin(comment).on(comment.article.eq(article)).fetchJoin()
//                .where(
//                        articleTitleContains(kw)
//                                .or(articleContentContains(kw))
//                                .or(articleAuthorContains(kw))
//                                .or(commentContentContains(kw))
//                                .or(commentAuthorContains(kw))
//                )
//                .distinct()
//                .fetch().size();
//
//        return new PageImpl<>(articleList, pageable, total);
//    }

    @Override
    public Slice<ArticleDto> findAllByKeywordSlice(String kw, Pageable pageable) {

        List<ArticleDto> articleList = queryFactory
                .select(new QArticleDto(
                        article.id,
                        article.subject,
                        new QSiteUserDto(
                                article.author.username,
                                article.author.isPaid),
                        article.createDate,
                        comment.count()
                ))
                .from(article)
                .leftJoin(comment).on(comment.article.eq(article))
                .groupBy(article.id)
                .where(
                        articleTitleContains(kw)
                                .or(articleContentContains(kw))
                                .or(articleAuthorContains(kw))
                                .or(commentContentContains(kw))
                                .or(commentAuthorContains(kw))
                )
                .distinct()
                .orderBy(article.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = articleList.size() > pageable.getPageSize();
        List<ArticleDto> content = hasNext ? articleList.subList(0, pageable.getPageSize()) : articleList;

        return new SliceImpl<>(content, pageable, hasNext);
    }
/////////////////////

    @Override
    public Page<ArticleDto> findAllByKeyword(final String sortCode, final String kwType, final String kw, final int page, Pageable pageable) {

        List<ArticleDto> articleList = queryFactory
                .select(new QArticleDto(
                        article.id,
                        article.subject,
                        article.content,
                        new QSiteUserDto(
                                article.author.username,
                                article.author.isPaid),
                        article.createDate,
                        comment.count(),
                        articleVote.countDistinct(),
                        article.viewCount
                ))
                .from(article)
                .leftJoin(comment).on(comment.article.eq(article))
                .leftJoin(articleVote).on(articleVote.article.eq(article))
                .groupBy(article.id)
                .where(
                        buildSearchPredicate(kwType, kw)
                )
                .distinct()
                .orderBy(sortType(sortCode))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(article)
                .leftJoin(comment).on(comment.article.eq(article)).fetchJoin()
                .where(
                        buildSearchPredicate(kwType, kw)
                )
                .distinct()
                .fetch().size();

        return new PageImpl<>(articleList, pageable, total);
    }

    public OrderSpecifier<?> sortType(String sortCodeCond) {
        if (sortCodeCond == null || sortCodeCond.isEmpty() || sortCodeCond.equals("idDesc")) {
            return article.id.desc();
        }

        if (sortCodeCond.equals("idAsc")) {
            return article.id.asc();
        }

        if (sortCodeCond.equals("hitDesc")) {
            return article.viewCount.desc();
        }
        if (sortCodeCond.equals("hitAsc")) {
            return article.viewCount.asc();
        }

        if (sortCodeCond.equals("likeCountDesc")) {
            return articleVote.count().desc();
        }
        if (sortCodeCond.equals("likeCountAsc")) {
            return articleVote.count().asc();
        }

        return article.id.desc();
    }

    public BooleanExpression buildSearchPredicate(final String kwType, final String kw) {
        BooleanExpression predicate =  Expressions.asBoolean(true).isTrue();
        if (kwType != null && !kwType.isEmpty()) {
            predicate = Expressions.asBoolean(false).isTrue();
            List<String> kwTypes = Arrays.stream(kwType.split(",")).toList();

            if (kwTypes.contains("title")) {
                predicate = predicate.or(articleTitleContains(kw));
            }
            if (kwTypes.contains("body")) {
                predicate = predicate.or(articleContentContains(kw));
            }
            if (kwTypes.contains("author")) {
                predicate = predicate.or(articleAuthorContains(kw));
            }
        }
        return predicate;
    }


    public BooleanExpression articleTitleContains(String articleTitleCond) {
        if (articleTitleCond == null || articleTitleCond.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }
        return article.subject.contains(articleTitleCond);
    }

    public BooleanExpression articleContentContains(String articleContentCond) {
        if (articleContentCond == null || articleContentCond.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }
        return article.content.contains(articleContentCond);
    }

    public BooleanExpression articleAuthorContains(String articleAuthorCond) {
        if (articleAuthorCond == null || articleAuthorCond.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }
        return article.author.username.contains(articleAuthorCond);
    }

    public BooleanExpression commentContentContains(String commentContentCond) {
        if (commentContentCond == null || commentContentCond.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }
        return comment.content.contains(commentContentCond);
    }

    public BooleanExpression commentAuthorContains(String commentAuthorCond) {
        if (commentAuthorCond == null || commentAuthorCond.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }
        return comment.author.username.contains(commentAuthorCond);
    }

}
