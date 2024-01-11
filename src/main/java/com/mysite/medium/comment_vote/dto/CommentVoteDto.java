package com.mysite.medium.comment_vote.dto;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class CommentVoteDto {
    private Long id;
    private Article articleDto;
    private CommentDto commentDto;
    private MemberDto memberDto;
}
