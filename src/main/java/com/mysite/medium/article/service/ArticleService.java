package com.mysite.medium.article.service;

import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.entity.Article;
import com.mysite.medium.user.dto.SiteUserDto;
import org.springframework.data.domain.Page;

public interface ArticleService {

    Page<ArticleDto> getArticleAll(int page, String kw);

    ArticleDto findArticleByArticleId(Long id);

    void createArticle(ArticleDto articleDto, SiteUserDto siteUserDto);

    void modifyArticle(Long articleId, ArticleDto articleDto);

    void deleteArticle(Long articleId);

//    ArticleDto articleToArticleDto(Article article);

}
