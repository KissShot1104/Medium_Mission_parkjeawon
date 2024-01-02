package com.mysite.medium.article.service;

import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.dto.ArticleMapper;
import com.mysite.medium.article.entity.Article;
import com.mysite.medium.article.repository.ArticleRepository;
import com.mysite.medium.global.exception.EntityNotFoundException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.SiteUserDtoMapper;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.repository.UserRepository;
import com.mysite.medium.user.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;
    private final SiteUserDtoMapper siteUserDtoMapper;

    public Page<ArticleDto> getArticleAll(final String sortCode, final String kwType, final String kw, final int page) {

        Pageable pageable = PageRequest.of(page, 10);
        return this.articleRepository.findAllByKeyword(sortCode, kwType, kw, page, pageable);
    }

    public Slice<ArticleDto> getArticleSliceAll(final int page, final String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAllByKeywordSlice(kw, pageable);
    }

    @Transactional
    public ArticleDto findArticleByArticleId(final Long id) {
        final Article article = this.articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        article.incrementViewCount();//
        final ArticleDto articleDto = articleMapper.articleToArticleDto(article);


        return articleDto;
    }

    @Transactional
    public Long createArticle(final ArticleDto articleDto, final Principal principal) {


        final SiteUser siteUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        final Article article = Article.builder()//수정 바람
                .subject(articleDto.getSubject())
                .content(articleDto.getContent())
                .author(siteUser)
                .viewCount(0L)
                .build();

        final Article savedArticle = articleRepository.save(article);
        return savedArticle.getId();
    }

    @Transactional
    public void modifyArticle(final Long articleId, final ArticleDto articleDto, final Principal principal) {

        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        article.checkAuthor(principal);

        article.modifyArticle(articleDto);
    }

    public void deleteArticle(final Long articleId, final Principal principal) {

        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        article.checkAuthor(principal);

        articleRepository.delete(article);
    }

}
