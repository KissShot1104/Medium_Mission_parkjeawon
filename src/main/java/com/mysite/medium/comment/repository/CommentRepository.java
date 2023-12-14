package com.mysite.medium.comment.repository;

import com.mysite.medium.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    void deleteAllByArticleId(Long articleId);
    List<Comment> findAllByArticleId(Long articleId);
}