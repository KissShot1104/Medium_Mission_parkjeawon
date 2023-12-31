package com.mysite.medium.comment.controller;

import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.article.service.ArticleService;
import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.service.CommentService;
import com.mysite.medium.comment_vote.entity.CommentVote;
import com.mysite.medium.comment_vote.service.CommentVoteService;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.service.UserService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final CommentVoteService commentVoteService;

//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/create/{articleId}")
//    public String createComment(Model model,
//                                @PathVariable("articleId") Long articleId,
//                                @Validated @ModelAttribute("commentDto") CommentDto commentDto,
//                                BindingResult bindingResult,
//                                Principal principal) {
//
//        ArticleDto articleDto = this.articleService.findArticleByArticleId(articleId);
//        SiteUserDto siteUser = this.userService.getUser(principal.getName());
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("article", articleDto);
//            return "article_detail";
//        }
//        Long commentId = commentService.createComment(articleId, commentDto, siteUser);
//
//        return String.format("redirect:/article/%s#comment_%s",
//                articleId, commentId);
//    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/modify/{commentId}")
//    public String modifyComment(CommentDto commentDto,
//                                @PathVariable("commentId") Long commentId,
//                                Principal principal) {
//
//        CommentDto existingComment = commentService.findCommentByCommendId(commentId);
//        if (!existingComment.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//
//        commentDto.setContent(existingComment.getContent());
//
//        return "comment_form";
//    }

//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/modify/{commentId}")
//    public String modifyComment(@Validated CommentDto commentDto,
//                                BindingResult bindingResult,
//                                @PathVariable("commentId") Long commentId,
//                                Principal principal) {
//
//        if (bindingResult.hasErrors()) {
//            return "comment_form";
//        }
//
//        CommentDto existingComment = commentService.findCommentByCommendId(commentId);
//        if (!existingComment.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//
//        commentService.modifyComment(commentId, commentDto);
//
//        return String.format("redirect:/article/%s#comment_%s",
//                existingComment.getArticle().getId(), existingComment.getId());
//    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/delete/{commentId}")
//    public String deleteComment(Principal principal,
//                                @PathVariable("commentId") Long commentId) {
//        CommentDto commentDto = commentService.findCommentByCommendId(commentId);
//        if (!commentDto.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//        }
//        commentVoteService.deleteCommentVoteAllByCommentId(commentId);
//        commentService.deleteComment(commentId);
//
//        return String.format("redirect:/article/%s", commentDto.getArticle().getId());
//    }

}