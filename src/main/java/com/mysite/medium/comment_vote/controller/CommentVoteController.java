package com.mysite.medium.comment_vote.controller;

import com.mysite.medium.comment_vote.service.CommentVoteService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CommentVoteController {
    private final CommentVoteService commentVoteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/{articleId}/comment/{commentId}/vote")
    public String voteComment(Principal principal,
                              @PathVariable("commentId") Long commentId) {

        commentVoteService.createCommentVote(commentId, principal.getName());

        return "redirect:/article/{articleId}";
    }

}
