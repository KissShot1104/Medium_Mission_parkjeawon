package com.mysite.medium.article_vote.controller;

import com.mysite.medium.article_vote.service.ArticleVoteService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleVoteRestController {
    private final ArticleVoteService articleVoteService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article/vote/{articleId}")
    public ResponseEntity<Void> toggleArticleVote(@PathVariable Long articleId,
                                            Principal principal) {

        articleVoteService.toggleArticleVote(articleId, principal.getName());

        return ResponseEntity.ok().build();
    }
}
