//package com.mysite.medium.article.controller;
//
//import com.mysite.medium.article.dto.ArticleDto;
//import com.mysite.medium.article.service.ArticleService;
//import com.mysite.medium.article_vote.dto.ArticleVoteDto;
//import com.mysite.medium.article_vote.service.ArticleVoteService;
//import com.mysite.medium.comment.dto.CommentDto;
//import com.mysite.medium.comment.service.CommentService;
//import com.mysite.medium.comment_vote.service.CommentVoteService;
//import com.mysite.medium.user.dto.SiteUserDto;
//import com.mysite.medium.user.service.UserService;
//import jakarta.validation.Valid;
//import java.security.Principal;
//import java.util.List;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.server.ResponseStatusException;
//
//@RequestMapping("/article")
//@RequiredArgsConstructor
//@Controller
//public class ArticleController {
//
//    private final ArticleService articleService;
//    private final CommentService commentService;
//    private final UserService userService;
//    private final ArticleVoteService articleVoteService;
//    private final CommentVoteService commentVoteService;
//
//    @GetMapping("/list")
//    public String listArticles(Model model,
//                               @RequestParam(value = "page", defaultValue = "0") int page,
//                               @RequestParam(value = "kw", defaultValue = "") String kw) {
//        Page<ArticleDto> paging = this.articleService.getArticleAll(page, kw);
//        model.addAttribute("paging", paging);
//        return "article_list";
//    }
//
//    @GetMapping("/{articleId}")
//    public String detailArticle(Model model,
//                                @PathVariable("articleId") Long articleId,
//                                @ModelAttribute(value = "commentDto") CommentDto commentDto,
//                                @RequestParam(value = "page", defaultValue = "0") int page) {
//
//        ArticleDto articleDto = articleService.findArticleByArticleId(articleId);
//        Page<CommentDto> pagingComment = commentService.findCommentAllByArticleId(page, articleId);
//        List<ArticleVoteDto> articleVoteDtos = articleVoteService.findArticleVoterAllByArticleId(articleId);
//        Map<Long, Long> commentVoteDtos = commentVoteService.getCommentLikesForArticle(articleId);//변수 이름 수정 바람
//
//        model.addAttribute("article", articleDto);
//        model.addAttribute("paging", pagingComment);
//        model.addAttribute("articleVote", articleVoteDtos);
//        model.addAttribute("commentVote", commentVoteDtos);
//        return "article_detail";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/create")
//    public String createArticle(ArticleDto articleDto) {
//        return "article_form";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/create")
//    public String createArticle(@Validated @ModelAttribute ArticleDto articleDto,
//                                BindingResult bindingResult,
//                                Principal principal) {
//
//        if (bindingResult.hasErrors()) {
//            return "article_form";
//        }
//
//        SiteUserDto siteUser = this.userService.getUser(principal.getName());
//        this.articleService.createArticle(articleDto, siteUser);
//
//        return "redirect:/article/list";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/modify/{articleId}")
//    public String modifyArticle(ArticleDto articleDto,
//                                @PathVariable("articleId") Long articleId,
//                                Principal principal,
//                                Model model) {
//
//        ArticleDto existingArticle = this.articleService.findArticleByArticleId(articleId);
//        if (!existingArticle.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//
//        model.addAttribute("articleDto", existingArticle);
//
//        return "article_form";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/modify/{articleId}")
//    public String modifyArticle(@Valid ArticleDto articleDto,
//                                BindingResult bindingResult,
//                                Principal principal,
//                                @PathVariable("articleId") Long articleId) {
//
//        if (bindingResult.hasErrors()) {
//            return "article_form";
//        }
//
//        ArticleDto existingArticle = this.articleService.findArticleByArticleId(articleId);
//
//        if (!existingArticle.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//
//        this.articleService.modifyArticle(articleId, articleDto);
//
//        return "redirect:/article/{articleId}";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/delete/{articleId}")
//    public String deleteArticle(Principal principal,
//                                @PathVariable("articleId") Long articleId) {
//        ArticleDto articleDto = this.articleService.findArticleByArticleId(articleId);
//        if (!articleDto.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//        }
//        this.articleService.deleteArticle(articleId);
//        return "redirect:/";
//    }
//
//}