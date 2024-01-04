package com.mysite.medium.comment.dto;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.member.dto.MemberDto;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Builder
public class CommentDto {

    private Long id;

    @NotBlank(message = "댓글 내용을 필수입니다.")
    private String content;

    private Article article;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private MemberDto author;//이름을 author? memberForm? member?

    @QueryProjection
    public CommentDto(Long id,
                      String content,
                      LocalDateTime createDate,
                      LocalDateTime modifyDate,
                      MemberDto author) {

        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.author = author;
    }

}