package com.mysite.medium.comment_vote.service;

import com.mysite.medium.comment_vote.dto.CommentVoteDto;
import com.mysite.medium.comment_vote.entity.CommentVote;
import java.util.Map;

public interface CommentVoteService {
    void createCommentVote(final Long commentId, final String username);
    void deleteCommentVoteAllByCommentId(Long commentId);
    CommentVoteDto articleVoteToArticleVoteDto(CommentVote commentVote);

    Map<Long, Long> getCommentLikesForArticle(Long articleId);
}
