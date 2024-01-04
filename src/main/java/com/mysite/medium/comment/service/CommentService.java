package com.mysite.medium.comment.service;

import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.user.dto.MemberDto;
import java.security.Principal;
import org.springframework.data.domain.Page;

public interface CommentService {

    Long createComment(final Long articleId, final CommentDto commentDto, final Principal principal);

    Page<CommentDto> findCommentAllByArticleId(final int page, final Long id);

    CommentDto findCommentByCommendId(final Long commentId);

    void modifyComment(final Long commentId, final CommentDto commentDto, final Principal principal);

    void deleteComment(final Long commentId, final Principal principal);

    void deleteAllByArticleId(final Long articleId);

}
