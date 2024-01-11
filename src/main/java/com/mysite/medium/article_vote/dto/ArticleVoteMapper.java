package com.mysite.medium.article_vote.dto;

import com.mysite.medium.article_vote.entity.ArticleVote;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleVoteMapper {

    ArticleVoteDto articleVoteToArticleVoteDto(ArticleVote articleVote);
    ArticleVote articleVoteDtoToArticleVote(ArticleVoteDto articleVoteDto);

    List<ArticleVoteDto> articleVoteListToArticleVoteDtoList(List<ArticleVote> articleVoteList);
}
