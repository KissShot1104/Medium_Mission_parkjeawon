package com.mysite.medium.global.security.provider;

import com.mysite.medium.global.security.AppConfig;
import java.time.Duration;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {
    private final String domain;

    public CookieProvider(AppConfig appConfig) {
        this.domain = appConfig.getDomain();
    }

    public ResponseCookie createAccessTokenCookie(String accessToken) {

        return ResponseCookie.from("accessToken", accessToken)
                .domain(domain)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofMinutes(30))
                .build();
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {

        return ResponseCookie.from("refreshToken", refreshToken)
                .domain(domain)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofDays(1))
                .build();
    }

    public ResponseCookie removeToken(String cookieName) {
        return ResponseCookie.from(cookieName, null)
                .domain(domain)
                .path("/")
                .maxAge(0)
                .build();
    }
}
