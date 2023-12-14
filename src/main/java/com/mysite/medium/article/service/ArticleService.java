package com.mysite.medium.article.service;

import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.entity.Article;
import com.mysite.medium.user.dto.SiteUserDto;
import org.springframework.data.domain.Page;

public interface ArticleService {

    public Page<ArticleDto> getArticleAll(int page, String kw);

    public ArticleDto findArticleByArticleId(Long id);

    public void createArticle(ArticleDto articleDto, SiteUserDto siteUserDto);

    public void modifyArticle(Long articleId, ArticleDto articleDto);

    public void deleteArticle(Long articleId);

    public void voteArticle(Long articleId, SiteUserDto siteUserDto);
    public ArticleDto articleToArticleDto(Article article);

}
