package com.mysite.medium.article_vote.controller;

import com.mysite.medium.article_vote.service.ArticleVoteService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ArticleVoteController {
    private final ArticleVoteService articleVoteService;

    //이거 이름을 article/vote로 바꿔야 하나?
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/vote/{articleId}")
    public String voteArticle(Principal principal,
                              @PathVariable("articleId") Long articleId) {

        articleVoteService.toggleArticleVote(articleId, principal.getName());

        return "redirect:/article/{articleId}";
    }
}
