package com.mysite.medium.comment_vote.dto;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.user.dto.SiteUserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class CommentVoteDto {
    private Long id;
    private Article articleDto;
    private CommentDto commentDto;
    private SiteUserDto siteUserDto;
}
