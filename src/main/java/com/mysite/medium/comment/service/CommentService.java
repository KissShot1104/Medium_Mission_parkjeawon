package com.mysite.medium.comment.service;

import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.user.dto.SiteUserDto;
import org.springframework.data.domain.Page;

public interface CommentService {

    public Long createComment(Long articleId, CommentDto commentDto, SiteUserDto author);

    public Page<CommentDto> findCommentAllByArticleId(int page, Long id);

    public CommentDto getComment(Long id);

    public void modifyComment(Long commentId , CommentDto commentDto);

    public void deleteComment(Long commentId);

    public void deleteAllByArticleId(Long articleId);

    public Comment commentDtoToComment(CommentDto commentDto);

    public CommentDto commentToCommentDto(Comment comment);

    public void vote(CommentDto commentDto, SiteUserDto siteUserDto);
}
