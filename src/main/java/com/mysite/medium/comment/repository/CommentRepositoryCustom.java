package com.mysite.medium.comment.repository;

import com.mysite.medium.comment.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {

    public Page<CommentDto> findCommentAll(Pageable pageable, Long articleId);
}
