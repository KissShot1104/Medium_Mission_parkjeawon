package com.mysite.medium.comment.repository;

import static com.mysite.medium.comment.entity.QComment.comment;

import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.dto.QCommentDto;
import com.mysite.medium.member.dto.QMemberDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<CommentDto> findCommentAll(Pageable pageable, Long articleId) {

        List<CommentDto> commentList = queryFactory
                .select(new QCommentDto(
                        comment.id,
                        comment.content,
                        comment.createDate,
                        comment.modifyDate,
                        new QMemberDto(
                                comment.author.username,
                                comment.author.isPaid
                        )
                ))
                .from(comment)
                .where(comment.article.id.eq(articleId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(comment)
                .from(comment)
                .where(comment.article.id.eq(articleId))
                .stream().count();

        return new PageImpl<>(commentList, pageable, total);
    }
}
