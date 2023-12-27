package com.mysite.medium.article.repository;

import com.mysite.medium.article.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {

    Page<ArticleDto> findAllByKeyword(String kw, Pageable pageable);
    Slice<ArticleDto> findAllByKeywordSlice(String kw, Pageable pageable);

}
