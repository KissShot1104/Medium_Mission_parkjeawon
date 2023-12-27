package com.mysite.medium.comment.dto;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.user.dto.SiteUserDto;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private Long id;

    @NotBlank(message = "댓글 내용을 필수입니다.")
    private String content;

    private Article article;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private SiteUserDto author;//이름을 author? siteUserForm? siteUser?

    @QueryProjection
    public CommentDto(Long id,
                      String content,
                      LocalDateTime createDate,
                      LocalDateTime modifyDate,
                      SiteUserDto author) {

        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.author = author;
    }

}