package com.mysite.medium.article.service;

import com.mysite.medium.article.dto.ArticleDto;
import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public interface ArticleService {

    Page<ArticleDto> getArticleAll(final int page, final String kw);

    Slice<ArticleDto> getArticleSliceAll(final int page, final String kw);
    ArticleDto findArticleByArticleId(final Long id);

    Long createArticle(final ArticleDto articleDto, final Principal principal);

    void modifyArticle(final Long articleId, final ArticleDto articleDto, final Principal principal);

    void deleteArticle(final Long articleId, final Principal principal);

}
