package com.mysite.medium.article_vote.dto;

import com.mysite.medium.article_vote.dto.ArticleVoteDto.ArticleVoteDtoBuilder;
import com.mysite.medium.article_vote.entity.ArticleVote;
import com.mysite.medium.article_vote.entity.ArticleVote.ArticleVoteBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-01T21:29:22+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.1 (Azul Systems, Inc.)"
)
@Component
public class ArticleVoteMapperImpl implements ArticleVoteMapper {

    @Override
    public ArticleVoteDto articleVoteToArticleVoteDto(ArticleVote articleVote) {
        if ( articleVote == null ) {
            return null;
        }

        ArticleVoteDtoBuilder articleVoteDto = ArticleVoteDto.builder();

        articleVoteDto.id( articleVote.getId() );

        return articleVoteDto.build();
    }

    @Override
    public ArticleVote articleVoteDtoToArticleVote(ArticleVoteDto articleVoteDto) {
        if ( articleVoteDto == null ) {
            return null;
        }

        ArticleVoteBuilder articleVote = ArticleVote.builder();

        articleVote.id( articleVoteDto.getId() );

        return articleVote.build();
    }

    @Override
    public List<ArticleVoteDto> articleVoteListToArticleVoteDtoList(List<ArticleVote> articleVoteList) {
        if ( articleVoteList == null ) {
            return null;
        }

        List<ArticleVoteDto> list = new ArrayList<ArticleVoteDto>( articleVoteList.size() );
        for ( ArticleVote articleVote : articleVoteList ) {
            list.add( articleVoteToArticleVoteDto( articleVote ) );
        }

        return list;
    }
}
