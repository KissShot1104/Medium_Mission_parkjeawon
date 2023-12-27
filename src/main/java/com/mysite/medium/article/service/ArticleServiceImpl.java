package com.mysite.medium.article.service;

import com.mysite.medium.DataNotFoundException;
import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.dto.ArticleMapper;
import com.mysite.medium.article.entity.Article;
import com.mysite.medium.article.repository.ArticleRepository;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.SiteUserDtoMapper;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final ArticleMapper articleMapper;
    private final SiteUserDtoMapper siteUserDtoMapper;

    public Page<ArticleDto> getArticleAll(final int page, final String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAllByKeyword(kw, pageable);
    }

    public ArticleDto findArticleByArticleId(final Long id) {
        final Optional<Article> article = this.articleRepository.findById(id);

        if (article.isEmpty()) {
            throw new DataNotFoundException("article not found");
        }

        final ArticleDto articleDto = articleMapper.articleToArticleDto(article.get());

        return articleDto;
    }

    @Transactional
    public void createArticle(final ArticleDto articleDto, final SiteUserDto siteUserDto) {

        final SiteUser siteUser = siteUserDtoMapper.siteUserDtoToSiteUser(siteUserDto);

        Article article = Article.builder()//수정 바람
                .subject(articleDto.getSubject())
                .content(articleDto.getContent())
                .author(siteUser)
                .build();

        this.articleRepository.save(article);
    }

    @Transactional
    public void modifyArticle(final Long articleId, final ArticleDto articleDto) {

        final Optional<Article> article = articleRepository.findById(articleId);

        if (article.isEmpty()) {
            throw new DataNotFoundException("article not found");
        }

        article.get().modifyArticle(articleDto);
    }

    public void deleteArticle(final Long articleId) {

        final Optional<Article> article = articleRepository.findById(articleId);

        if (article.isEmpty()) {
            throw new DataNotFoundException("article not found");
        }

        this.articleRepository.delete(article.get());
    }

}