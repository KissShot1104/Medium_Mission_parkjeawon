package com.mysite.medium.article.repository;


import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.dto.QArticleDto;

import com.mysite.medium.user.dto.QSiteUserDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import static com.mysite.medium.article.entity.QArticle.article;
import static com.mysite.medium.comment.entity.QComment.comment;


public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public ArticleRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);

    }


    @Override
    public Page<ArticleDto> findAllByKeyword(String kw, Pageable pageable) {

        List<ArticleDto> articleList = queryFactory
                .select(new QArticleDto(
                        article.id,
                        article.subject,
                        new QSiteUserDto(article.author.username),
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
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(article)
                .leftJoin(comment).on(comment.article.eq(article)).fetchJoin()
                .where(
                        articleTitleContains(kw)
                                .or(articleContentContains(kw))
                                .or(articleAuthorContains(kw))
                                .or(commentContentContains(kw))
                                .or(commentAuthorContains(kw))
                )
                .distinct()
                .fetch().size();

        return new PageImpl<>(articleList, pageable, total);
    }

    @Override
    public Slice<ArticleDto> findAllByKeywordSlice(String kw, Pageable pageable) {

        List<ArticleDto> articleList = queryFactory
                .select(new QArticleDto(
                        article.id,
                        article.subject,
                        new QSiteUserDto(article.author.username),
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
