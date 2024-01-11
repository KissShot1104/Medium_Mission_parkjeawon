package com.mysite.medium.article_vote.service;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.article.repository.ArticleRepository;
import com.mysite.medium.article_vote.dto.ArticleVoteDto;
import com.mysite.medium.article_vote.dto.ArticleVoteMapper;
import com.mysite.medium.article_vote.entity.ArticleVote;
import com.mysite.medium.article_vote.repository.ArticleVoteRepository;
import com.mysite.medium.global.exception.EntityNotFoundException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.member.entity.Member;
import com.mysite.medium.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleVoteServiceImpl implements ArticleVoteService {

    //어떨때 repository를 부르고 어떨 때 Service를 불러야 하는가?
    private final ArticleVoteRepository articleVoteRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    private final ArticleVoteMapper articleVoteMapper;


    @Transactional
    public void toggleArticleVote(final Long articleId, final String username) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        final Member user = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        final Optional<ArticleVote> articleVote = articleVoteRepository.findByArticleIdAndUserId(articleId,
                user.getId());

        if (articleVote.isEmpty()) {
            createArticleVote(article, user);
        } else {
            deleteArticleVoteByArticleVote(articleVote.get());
        }
    }

    private void createArticleVote(final Article article, final Member user) {
        final ArticleVote articleVote = ArticleVote.builder()
                .article(article)
                .user(user)
                .build();

        articleVoteRepository.save(articleVote);
    }

    private void deleteArticleVoteByArticleVote(final ArticleVote articleVote) {
        articleVoteRepository.delete(articleVote);
    }

    public List<ArticleVoteDto> findArticleVoterAllByArticleId(final Long articleId) {
        final List<ArticleVote> articleVoteList = articleVoteRepository
                .findAllByArticleId(articleId);

        final List<ArticleVoteDto> articleVoteDtoList = articleVoteMapper
                .articleVoteListToArticleVoteDtoList(articleVoteList);

        return articleVoteDtoList;
    }

    @Transactional
    public void deleteArticleVoteAllByArticleId(final Long articleId) {
        articleVoteRepository.deleteAllByArticleId(articleId);
    }

}
