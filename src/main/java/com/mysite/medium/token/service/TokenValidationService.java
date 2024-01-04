package com.mysite.medium.token.service;

import com.mysite.medium.global.security.provider.JwtTokenProvider;
import com.mysite.medium.token.entity.Token;
import com.mysite.medium.user.service.UserService;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;

    public void validateRefreshTokenNotEmpty(String memberId, String refreshToken)
            throws AuthenticationException {
        if (!StringUtils.hasText(refreshToken)) {
            tokenService.deleteToken(memberId);
            throw new AuthenticationException("잘못된 접근입니다. 재로그인이 필요합니다.");
        }
    }

    public void validateRefreshToken(String memberId, String refreshToken)
            throws AuthenticationException {
        Token token = tokenService.getToken(memberId);

        if (!refreshToken.equals(token.getRefreshToken()) || !jwtTokenProvider.validateToken(token.getRefreshToken())) {
            throw new AuthenticationException("재로그인이 필요합니다.");
        }
    }

    public String refreshTokens(String memberId) {
        List<String> memberAuthorities = userService.getRoles(memberId);
        updateRefreshToken(memberId, jwtTokenProvider.createRefreshToken(memberId));

        return jwtTokenProvider.createAccessToken(memberId, memberAuthorities);
    }

    public void updateRefreshToken(String memberId, String refreshToken) {
        tokenService.deleteToken(memberId);
        tokenService.register(refreshToken, Long.valueOf(memberId));
    }



}
