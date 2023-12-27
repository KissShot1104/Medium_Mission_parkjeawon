package com.mysite.medium.article.entity;

import com.mysite.medium.article.dto.ArticleDto;
import jakarta.persistence.JoinColumn;

import com.mysite.medium.global.BaseEntity;
import com.mysite.medium.user.entity.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    public void modifyArticle(ArticleDto articleDto) {
        this.subject = articleDto.getSubject();
        this.content = articleDto.getContent();
    }

}