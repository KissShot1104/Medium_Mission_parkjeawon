package com.mysite.medium.token.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String refreshToken;

    private Long memberId;

    @Builder
    public Token(String refreshToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }
}
