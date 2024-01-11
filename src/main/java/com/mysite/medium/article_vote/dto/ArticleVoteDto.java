package com.mysite.medium.article_vote.dto;


import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class ArticleVoteDto {

    private Long id;

    private MemberDto memberDto;
    private ArticleDto articleDto;

}
