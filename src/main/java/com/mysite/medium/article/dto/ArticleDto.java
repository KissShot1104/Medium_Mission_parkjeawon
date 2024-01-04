package com.mysite.medium.article.dto;

import com.mysite.medium.user.dto.SiteUserDto;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class ArticleDto {

    Long id;

    @NotBlank(message = "제목은 필수항목입니다.")
    @Size(max = 200)
    private String subject;

    @NotBlank(message = "내용은 필수항목입니다.")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private SiteUserDto author;

    private Long commentCount;

    private Boolean articlePaid;

    private Long voteCount;

    private Long viewCount;

    @QueryProjection
    public ArticleDto(Long id, String subject, SiteUserDto author, LocalDateTime createDate, Long commentCount) {
        this.id = id;
        this.subject = subject;
        this.author = author;
        this.createDate = createDate;
        this.commentCount = commentCount;
    }

    @QueryProjection
    public ArticleDto(Long id,
                      String subject,
                      String content,
                      SiteUserDto author,
                      LocalDateTime createDate,
                      Long commentCount,
                      Long voteCount,
                      Long viewCount) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.author = author;
        this.createDate = createDate;
        this.commentCount = commentCount;
        this.voteCount = voteCount;
        this.viewCount = viewCount;
    }

    @QueryProjection
    public ArticleDto(Long id, String subject, SiteUserDto author, LocalDateTime createDate) {
        this.id = id;
        this.subject = subject;
        this.author = author;
        this.createDate = createDate;
    }
}