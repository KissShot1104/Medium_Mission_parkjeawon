package com.mysite.medium.comment_vote.dto;

import com.mysite.medium.comment_vote.dto.CommentVoteDto.CommentVoteDtoBuilder;
import com.mysite.medium.comment_vote.entity.CommentVote;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-27T14:47:38+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.1 (Azul Systems, Inc.)"
)
@Component
public class CommentVoteMapperImpl implements CommentVoteMapper {

    @Override
    public CommentVoteDto articleVoteToArticleVoteDto(CommentVote commentVote) {
        if ( commentVote == null ) {
            return null;
        }

        CommentVoteDtoBuilder commentVoteDto = CommentVoteDto.builder();

        commentVoteDto.id( commentVote.getId() );

        return commentVoteDto.build();
    }
}
