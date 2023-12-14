package com.mysite.medium.article.entity;

import com.mysite.medium.article.dto.ArticleDto;
import jakarta.persistence.JoinColumn;

import com.mysite.medium.DateTime;
import com.mysite.medium.user.entity.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article extends DateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id")
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name="site_user_id")
    private SiteUser author;

    public void modifyArticle(ArticleDto articleDto) {
        this.subject = articleDto.getSubject();
        this.content = articleDto.getContent();
    }

}