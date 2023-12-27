package com.mysite.medium.comment_vote.service;

import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.user.entity.SiteUser;
import java.util.Map;

public interface CommentVoteService {
    void toggleCommentVote(final Long commentId, final String username);
    void createCommentVote(final Comment comment, final SiteUser user);
    void deleteCommentVoteAllByCommentId(final Long commentId);
    void deleteCommentVoteAllByArticleId(final Long articleId);
    Map<Long, Long> getCommentLikesForArticle(final Long articleId);
}
