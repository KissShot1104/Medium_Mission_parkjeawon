package com.mysite.medium.comment.service;

import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.comment.repository.CommentRepository;
import com.mysite.medium.user.dto.SiteUserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysite.medium.article.repository.ArticleRepository;
import com.mysite.medium.user.repository.UserRepository;
import com.mysite.medium.user.service.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.mysite.medium.DataNotFoundException;
import com.mysite.medium.article.entity.Article;
import com.mysite.medium.user.entity.SiteUser;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    public Page<CommentDto> findCommentAllByArticleId(int page, Long id) {
        List<Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.commentRepository.findCommentAll(pageable, id);
    }

    public Long createComment(Long articleId, CommentDto commentDto, SiteUserDto author) {

        Optional<Article> article = articleRepository.findById(articleId);

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .article(article.get())
                .author(userServiceImpl.siteUserFormToSiteUser(author))
                .build();

        this.commentRepository.save(comment);

        return comment.getId();
    }
    
    public CommentDto getComment(Long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);

        if (comment.isEmpty()) {
            throw new DataNotFoundException("comment not found");
        }

        SiteUserDto siteUser = userServiceImpl.siteUserToSiteUserForm(comment.get().getAuthor());

        return CommentDto.builder()
                .id(comment.get().getId())
                .content(comment.get().getContent())
                .article(comment.get().getArticle())
                .author(siteUser)
                .build();
    }

    @Transactional
    public void modifyComment(Long commentId , CommentDto commentDto) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isEmpty()) {
            throw new DataNotFoundException("Not Found Comment");
        }

        comment.get().modifyComment(commentDto);
    }

    @Transactional
    public void deleteComment(Long commentId) {

        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isEmpty()) {
            throw new DataNotFoundException("Not Found Comment");
        }

        this.commentRepository.delete(comment.get());
    }

    @Transactional
    public void deleteAllByArticleId(Long articleId) {
        this.commentRepository.deleteAllByArticleId(articleId);
    }
    
    public void vote(CommentDto commentDto, SiteUserDto siteUserDto) {

        Comment comment = commentDtoToComment(commentDto);

        this.commentRepository.save(comment);
    }


    public Comment commentDtoToComment(CommentDto commentDto) {
        SiteUser author = userServiceImpl.siteUserFormToSiteUser(commentDto.getAuthor());

        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .article(commentDto.getArticle())
                .author(author)
                .build();
    }

    public CommentDto commentToCommentDto(Comment comment) {

        SiteUserDto author = userServiceImpl.siteUserToSiteUserForm(comment.getAuthor());

        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .article(comment.getArticle())
                .createDate(comment.getCreateDate())
                .modifyDate(comment.getModifyDate())
                .author(author)
                .build();
    }

}