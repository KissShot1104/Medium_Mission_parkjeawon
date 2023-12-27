package com.mysite.medium.comment.service;

import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.user.dto.SiteUserDto;
import org.springframework.data.domain.Page;

public interface CommentService {

    Long createComment(final Long articleId, final CommentDto commentDto, final SiteUserDto author);

    Page<CommentDto> findCommentAllByArticleId(final int page, final Long id);

    CommentDto findCommentByCommendId(final Long commentId);

    void modifyComment(final Long commentId, final CommentDto commentDto);

    void deleteComment(final Long commentId);

    void deleteAllByArticleId(final Long articleId);

}
