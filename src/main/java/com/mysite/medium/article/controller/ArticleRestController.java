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
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ArticleRestController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final ArticleVoteService articleVoteService;
    private final CommentVoteService commentVoteService;

    @GetMapping("/")
    public ResponseEntity<Slice<ArticleDto>> listArticles(@RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "kw", defaultValue = "") String kw) {

        Slice<ArticleDto> slice = this.articleService.getArticleSliceAll(page, kw);
        return ResponseEntity.ok(slice);
    }

    /*@PostMapping("/")
    public ResponseEntity<Void> createArticle(@RequestBody @Valid ArticleDto articleDto,
                                              BindingResult bindingResult,
                                              Principal principal) {

        articleService.createArticle(articleDto, principal);
        return ResponseEntity.created
    }*/

    @GetMapping("/article/{articleId}")
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