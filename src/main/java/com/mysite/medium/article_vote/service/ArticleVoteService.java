package com.mysite.medium.article_vote.service;

import com.mysite.medium.article_vote.dto.ArticleVoteDto;
import java.util.List;

public interface ArticleVoteService {

    void toggleArticleVote(final Long articleId, final String username);
    List<ArticleVoteDto> findArticleVoterAllByArticleId(final Long articleId);
    void deleteArticleVoteAllByArticleId(final Long articleId);

}
