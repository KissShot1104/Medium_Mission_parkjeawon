package com.mysite.medium.article.dto;

import com.mysite.medium.user.dto.SiteUserDto;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ArticleDto {

    Long id;

    @NotBlank(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String subject;

    @NotBlank(message="내용은 필수항목입니다.")
    private String content;

    private LocalDateTime createDate;
    
    private LocalDateTime modifyDate;

    private SiteUserDto author;

    private Long commentCount;

    @QueryProjection
    public ArticleDto(Long id, String subject, SiteUserDto author, LocalDateTime createDate, Long commentCount) {
        this.id = id;
        this.subject = subject;
        this.author = author;
        this.createDate = createDate;
        this.commentCount = commentCount;
    }

    @QueryProjection
    public ArticleDto(Long id, String subject, String content, SiteUserDto author, LocalDateTime createDate, Long commentCount) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.author = author;
        this.createDate = createDate;
    }

    @QueryProjection
    public ArticleDto(Long id, String subject, SiteUserDto author, LocalDateTime createDate) {
        this.id = id;
        this.subject = subject;
        this.author = author;
        this.createDate = createDate;
    }

}