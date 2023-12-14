package com.mysite.medium.article_vote.repository;

import com.mysite.medium.article_vote.entity.ArticleVote;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleVoteRepository extends JpaRepository<ArticleVote, Long> {
    Optional<ArticleVote> findByArticleIdAndUserId(Long articleId, Long siteUserId);

    List<ArticleVote> findAllByArticleId(Long articleId);

    void deleteAllByArticleId(Long articleId);
}
