package com.mysite.medium.article.repository;

import com.mysite.medium.article.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {

//    Page<ArticleDto> findAllByKeyword(String kw, Pageable pageable);

    Page<ArticleDto> findAllByKeyword(final String sortCode, final String kwType, final String kw, final int page, Pageable pageable);

    Slice<ArticleDto> findAllByKeywordSlice(String kw, Pageable pageable);

}
