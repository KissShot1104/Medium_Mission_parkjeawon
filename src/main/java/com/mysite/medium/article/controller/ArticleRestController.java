package com.mysite.medium.article.controller;

import com.mysite.medium.article.dto.ArticleDetailDto;
import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.service.ArticleService;
import com.mysite.medium.article_vote.dto.ArticleVoteDto;
import com.mysite.medium.article_vote.service.ArticleVoteService;
import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.service.CommentService;
import com.mysite.medium.comment_vote.service.CommentVoteService;
import com.mysite.medium.user.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleRestController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final ArticleVoteService articleVoteService;
    private final CommentVoteService commentVoteService;

    @GetMapping
    public ResponseEntity<Page<ArticleDto>> listArticles(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "kw", defaultValue = "") String kw) {

        Page<ArticleDto> articleAll = this.articleService.getArticleAll(page, kw);
        return ResponseEntity.ok(articleAll);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Void> createArticle(@RequestPart @Valid ArticleDto articleDto,
                                              Principal principal) {

        final Long savedId = articleService.createArticle(articleDto, principal);
        return ResponseEntity.created(URI.create("/article/" + savedId)).build();
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{articleId}")
    public ResponseEntity<Void> modifyArticle(@RequestPart @Valid ArticleDto articleDto,
                                              @PathVariable("articleId") Long articleId,
                                              Principal principal) {

        articleService.modifyArticle(articleId, articleDto, principal);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("articleId") Long articleId,
                                              Principal principal) {
        articleService.deleteArticle(articleId, principal);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDetailDto> detailArticle(@PathVariable("articleId") Long articleId,
                                                          @RequestParam(value = "page", defaultValue = "0") int page) {

        ArticleDto articleDto = articleService.findArticleByArticleId(articleId);
        Page<CommentDto> pagingComment = commentService.findCommentAllByArticleId(page, articleId);
        List<ArticleVoteDto> articleVoteDtos = articleVoteService.findArticleVoterAllByArticleId(articleId);
        Map<Long, Long> commentVoteDtos = commentVoteService.getCommentLikesForArticle(articleId);//변수 이름 수정 바람

        ArticleDetailDto articleDetailDto = ArticleDetailDto.builder()
                .articleDto(articleDto)
                .pagingComment(pagingComment)
                .articleVoteDtos(articleVoteDtos)
                .commentVoteDtos(commentVoteDtos)
                .build();

        return ResponseEntity.ok(articleDetailDto);
    }

}
