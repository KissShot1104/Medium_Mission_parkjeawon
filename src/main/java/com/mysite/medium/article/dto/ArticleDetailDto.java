package com.mysite.medium.article.dto;

import com.mysite.medium.article_vote.dto.ArticleVoteDto;
import com.mysite.medium.comment.dto.CommentDto;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ArticleDetailDto {

    private ArticleDto articleDto;
    private Page<CommentDto> pagingComment;
    private List<ArticleVoteDto>articleVoteDtos;
    private Map<Long, Long> commentVoteDtos;
}
