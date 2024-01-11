package com.mysite.medium.comment_vote.dto;

import com.mysite.medium.comment_vote.entity.CommentVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentVoteMapper {
    CommentVoteDto articleVoteToArticleVoteDto(final CommentVote commentVote);
}
