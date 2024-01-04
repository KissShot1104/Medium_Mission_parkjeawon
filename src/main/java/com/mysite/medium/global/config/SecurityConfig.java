package com.mysite.medium.global.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.nio.charset.StandardCharsets;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 추가된 코드
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authenticationManager = sharedObject.build();

        http.authenticationManager(authenticationManager);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors().and()
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//                .authorizeHttpRequests(authorizeRequest ->
//                        authorizeRequest
//                                .requestMatchers(
//                                        antMatcher("/auth/**")
//                                ).authenticated()
//                                .requestMatchers(
//                                        antMatcher("/h2-console/**")
//                                ).permitAll()
//                )
                // 추가된 코드
                .addFilterAt(//우리가 만든 로그인 요청 필터가  동작하도록 하기 위해 필터를 바꿔치기함
                        this.abstractAuthenticationProcessingFilter(
                                authenticationManager,
                                authenticationSuccessHandler()),
                        UsernamePasswordAuthenticationFilter.class)
                .logout(logoutConfig ->
                        logoutConfig
                                .logoutUrl("/user/logout")
                                .logoutSuccessHandler(
                                        ((request, response, authentication) -> {
                                            System.out.println("로그아웃 성공");

                                            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                            response.getWriter().println("로그아웃 성공!");
                                        })
                                ))
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                                        .contentSecurityPolicy( policyConfig ->
                                                policyConfig.policyDirectives(
                                                        "script-src 'self'; " + "img-src 'self'; " +
                                                                "font-src 'self' data:; " + "default-src 'self'; " +
                                                                "frame-src 'self'"
                                                )
                                        )
                );

        return http.build();
    }

    // 추가된 코드
    public AbstractAuthenticationProcessingFilter abstractAuthenticationProcessingFilter(final AuthenticationManager authenticationManager,
                                                                                         final AuthenticationSuccessHandler authenticationSuccessHandler
    ) {
        return new LoginAuthenticationFilter(
                "/user/login",
                authenticationManager,
                authenticationSuccessHandler
        );
    }

    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new BootAuthenticationSuccessHandler();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스 spring security 대상에서 제외
        return (web) ->
                web
                        .ignoring()
                        .requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations()
                        );
    }
}
