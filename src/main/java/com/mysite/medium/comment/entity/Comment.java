package com.mysite.medium.comment.entity;

import com.mysite.medium.article.entity.Article;
import com.mysite.medium.comment.dto.CommentDto;
import com.mysite.medium.global.exception.AuthException;
import com.mysite.medium.global.exception.ErrorCode;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import com.mysite.medium.global.BaseEntity;
import com.mysite.medium.member.entity.Member;

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
@Setter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Builder
@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch= FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne(fetch= FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="member_id")
    private Member author;

    public void modifyComment(CommentDto commentDto) {
        this.content = commentDto.getContent();
    }

    public void checkCommentAuthor(Principal principal) {
        if (!this.author.getUsername().equals(principal.getName())) {
            throw new AuthException(ErrorCode.UNAUTHORIZED_USER);
        }
    }
}