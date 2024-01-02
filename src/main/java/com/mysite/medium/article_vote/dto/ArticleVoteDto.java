package com.mysite.medium.article_vote.dto;


import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.user.dto.SiteUserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class ArticleVoteDto {

    private Long id;

    private SiteUserDto siteUserDto;
    private ArticleDto articleDto;

}
