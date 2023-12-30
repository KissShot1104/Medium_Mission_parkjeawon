package com.mysite.medium.article.service;

import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.entity.Article;
import com.mysite.medium.user.dto.SiteUserDto;
import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public interface ArticleService {

    Page<ArticleDto> getArticleAll(int page, String kw);

    Slice<ArticleDto> getArticleSliceAll(final int page, final String kw);
    ArticleDto findArticleByArticleId(Long id);

    Long createArticle(ArticleDto articleDto, Principal principal);

    void modifyArticle(Long articleId, ArticleDto articleDto);

    void deleteArticle(Long articleId);

//    ArticleDto articleToArticleDto(Article article);

}
