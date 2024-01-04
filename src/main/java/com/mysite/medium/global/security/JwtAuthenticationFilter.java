package com.mysite.medium.global.security;


import com.mysite.medium.global.security.provider.CookieProvider;
import com.mysite.medium.global.security.provider.JwtTokenProvider;
import com.mysite.medium.token.service.TokenValidationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;
    private final TokenValidationService tokenValidationService;

    private String getTokenFromRequest(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getTokenFromRequest(request, "accessToken");

        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtTokenProvider.validateToken(accessToken)){
            String memberId = jwtTokenProvider.getClaims(accessToken).getSubject();
            String refreshToken = getTokenFromRequest(request, "refreshToken");

            tokenValidationService.validateRefreshTokenNotEmpty(memberId, refreshToken);
            tokenValidationService.validateRefreshToken(memberId, refreshToken);

            accessToken = tokenValidationService.refreshTokens(memberId);

            response.setHeader(HttpHeaders.SET_COOKIE, cookieProvider.createAccessTokenCookie(accessToken).toString());
            response.setHeader(HttpHeaders.SET_COOKIE, cookieProvider.createRefreshTokenCookie(refreshToken).toString());
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
