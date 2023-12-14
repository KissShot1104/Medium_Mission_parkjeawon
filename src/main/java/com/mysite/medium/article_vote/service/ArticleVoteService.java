package com.mysite.medium.article_vote.service;

import com.mysite.medium.article_vote.dto.ArticleVoteDto;
import java.util.List;

public interface ArticleVoteService {

    void toggleArticleVote(final Long articleId, final String username);
    List<ArticleVoteDto> findArticleVoterAllByArticleId(Long articleId);
    void deleteArticleVoteAllByArticleId(Long articleId);

}
