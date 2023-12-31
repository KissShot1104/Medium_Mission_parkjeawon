package com.mysite.medium.comment_vote.controller;

import com.mysite.medium.comment.service.CommentService;
import com.mysite.medium.comment_vote.service.CommentVoteService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentVoteRestController {
    private final CommentVoteService commentVoteService;


    @PostMapping("comment/vote/{commentId}")
    public ResponseEntity<Void> toggleCommentVote(@PathVariable("commentId") Long commentId,
                                                  Principal principal) {

        commentVoteService.toggleCommentVote(commentId, principal);

        return ResponseEntity.ok().build();
    }
}
