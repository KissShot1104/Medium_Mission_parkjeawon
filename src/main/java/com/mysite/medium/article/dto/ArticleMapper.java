package com.mysite.medium.article.dto;

import com.mysite.medium.article.entity.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleDto articleToArticleDto(Article article);
    Article articleDtoToArticle(ArticleDto article);

}
