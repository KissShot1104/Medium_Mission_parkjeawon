package com.mysite.medium.article.entity;

import com.mysite.medium.article.dto.ArticleDto;
import com.mysite.medium.global.exception.AuthException;
import com.mysite.medium.global.exception.ErrorCode;
import jakarta.persistence.JoinColumn;

import com.mysite.medium.global.BaseEntity;
import com.mysite.medium.user.entity.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.security.Principal;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id")
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="site_user_id")
    private SiteUser author;

    private Long viewCount;

    public void modifyArticle(ArticleDto articleDto) {
        this.subject = articleDto.getSubject();
        this.content = articleDto.getContent();
    }

    public void checkAuthor(Principal principal) {
        if (!this.author.getUsername().equals(principal.getName())) {
            throw new AuthException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    public void incrementViewCount() {
        if (this.viewCount == null) {
            viewCount = 0L;
        }
        viewCount++;
    }

}