package com.mysite.medium.comment.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.mysite.medium.comment.dto.QCommentDto is a Querydsl Projection type for CommentDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentDto extends ConstructorExpression<CommentDto> {

    private static final long serialVersionUID = 792119081L;

    public QCommentDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifyDate, com.querydsl.core.types.Expression<? extends com.mysite.medium.user.dto.SiteUserDto> author) {
        super(CommentDto.class, new Class<?>[]{long.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, com.mysite.medium.user.dto.SiteUserDto.class}, id, content, createDate, modifyDate, author);
    }

}

