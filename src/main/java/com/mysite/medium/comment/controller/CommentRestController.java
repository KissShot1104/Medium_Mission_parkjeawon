package com.mysite.medium.comment.controller;

import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.service.CommentService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class CommentRestController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{articleId}/comment")
    public ResponseEntity<Void> createComment(@PathVariable("articleId") Long articleId,
                                              @Validated @RequestPart CommentDto commentDto,
                                              Principal principal) {

        commentService.createComment(articleId, commentDto, principal);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/comment/modify/{commentId}")
    public ResponseEntity<Void> modifyComment(@PathVariable("commentId") Long commentId,
                                              @Validated @RequestPart CommentDto commentDto,
                                              Principal principal) {

        commentService.modifyComment(commentId, commentDto, principal);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/comment/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId,
                                              Principal principal) {
        commentService.deleteComment(commentId, principal);

        return ResponseEntity.noContent().build();
    }
}
