package com.mysite.medium.article.service;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.repository.ArticleRepository;
import com.mysite.medium.comment.service.CommentService;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.medium.DataNotFoundException;
import com.mysite.medium.user.entity.SiteUser;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final CommentService commentService;

    public Page<ArticleDto> getArticleAll(int page, String kw) {
    	List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.articleRepository.findAllByKeyword(kw, pageable);
    }




    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    public ArticleDto findArticleByArticleId(Long id) {
        Optional<Article> article = this.articleRepository.findById(id);

        if (article.isEmpty()) {
            throw new DataNotFoundException("article not found");
        }

        SiteUserDto authorForm = userService.siteUserToSiteUserForm(article.get().getAuthor());



        return ArticleDto.builder()
                .id(article.get().getId())
                .subject(article.get().getSubject())
                .content(article.get().getContent())
                .author(authorForm)
                .createDate(article.get().getCreateDate())
                .modifyDate(article.get().getModifyDate())
                .build();
    }

    @Transactional
    public void createArticle(ArticleDto articleDto, SiteUserDto siteUserDto) {

        SiteUser siteUser = userService.siteUserFormToSiteUser(siteUserDto);

        Article q = Article.builder()
                .subject(articleDto.getSubject())
                .content(articleDto.getContent())
                .author(siteUser)
                .build();
        this.articleRepository.save(q);
    }

    @Transactional
    public void modifyArticle(Long articleId, ArticleDto articleDto) {

        Optional<Article> article = articleRepository.findById(articleId);

        if (article.isEmpty()) {
            throw new DataNotFoundException("article not found");
        }

        article.get().modifyArticle(articleDto);

    }
    
    public void deleteArticle(Long articleId) {

        Optional<Article> article = articleRepository.findById(articleId);

        if (article.isEmpty()) {
            throw new DataNotFoundException("article not found");
        }

        this.articleRepository.delete(article.get());
    }

    @Transactional
    public void voteArticle(Long articleId, SiteUserDto siteUserDto) {

        Optional<Article> articlie = articleRepository.findById(articleId);

        if (articlie.isEmpty()) {
            throw new DataNotFoundException("article not found");
        }

        SiteUser siteUser = userService.siteUserFormToSiteUser(siteUserDto);

//        articlie.get().getVoter().add(siteUser);

        this.articleRepository.save(articlie.get());
    }

    public ArticleDto articleToArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .subject(article.getSubject())
                .content(article.getContent())
                .createDate(article.getCreateDate())
                .modifyDate(article.getModifyDate())
                .author(userService.siteUserToSiteUserForm(article.getAuthor()))
                .build();

    }

}