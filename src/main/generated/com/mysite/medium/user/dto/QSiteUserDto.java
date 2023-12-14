package com.mysite.medium.user.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.mysite.medium.user.dto.QSiteUserDto is a Querydsl Projection type for SiteUserDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSiteUserDto extends ConstructorExpression<SiteUserDto> {

    private static final long serialVersionUID = 1490229990L;

    public QSiteUserDto(com.querydsl.core.types.Expression<String> username) {
        super(SiteUserDto.class, new Class<?>[]{String.class}, username);
    }

}

