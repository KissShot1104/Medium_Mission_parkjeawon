package com.mysite.medium.article.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.mysite.medium.article.dto.QArticleDto is a Querydsl Projection type for ArticleDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QArticleDto extends ConstructorExpression<ArticleDto> {

    private static final long serialVersionUID = -655159877L;

    public QArticleDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> subject, com.querydsl.core.types.Expression<? extends com.mysite.medium.user.dto.SiteUserDto> author, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDate, com.querydsl.core.types.Expression<Long> commentCount) {
        super(ArticleDto.class, new Class<?>[]{long.class, String.class, com.mysite.medium.user.dto.SiteUserDto.class, java.time.LocalDateTime.class, long.class}, id, subject, author, createDate, commentCount);
    }

    public QArticleDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> subject, com.querydsl.core.types.Expression<? extends com.mysite.medium.user.dto.SiteUserDto> author, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDate) {
        super(ArticleDto.class, new Class<?>[]{long.class, String.class, com.mysite.medium.user.dto.SiteUserDto.class, java.time.LocalDateTime.class}, id, subject, author, createDate);
    }

}

