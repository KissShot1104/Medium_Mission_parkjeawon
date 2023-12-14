package com.mysite.medium.comment_vote.repository;

import com.mysite.medium.comment_vote.entity.CommentVote;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    Optional<CommentVote> findByCommentIdAndUserId(final Long commentId, final Long userId);
    void deleteCommentVoteAllByCommentId(Long commentId);
    Long countByCommentId(Long commentId);
}
