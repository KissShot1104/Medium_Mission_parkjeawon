package com.mysite.medium.comment_vote.service;

import com.mysite.medium.comment.entity.Comment;
import com.mysite.medium.user.entity.SiteUser;
import java.security.Principal;
import java.util.Map;

public interface CommentVoteService {
    void toggleCommentVote(final Long commentId, final Principal principal);
    void createCommentVote(final Comment comment, final SiteUser user);
    void deleteCommentVoteAllByCommentId(final Long commentId);
    Map<Long, Long> getCommentLikesForArticle(final Long articleId);
}
