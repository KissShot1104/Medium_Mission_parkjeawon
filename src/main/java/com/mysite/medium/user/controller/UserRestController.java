package com.mysite.medium.user.controller;

import com.mysite.medium.global.exception.AuthException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.global.security.provider.CookieProvider;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.dto.UserLoginResponse;
import com.mysite.medium.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;
    private final CookieProvider cookieProvider;
//todo controller구현해야함


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestPart @Valid UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid SiteUserDto siteUserDto) {
        UserLoginResponse userLoginResponse = userService.login(siteUserDto.getUsername(), siteUserDto.getPassword());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, String.valueOf(
                        cookieProvider.createAccessTokenCookie(userLoginResponse.accessToken()))
                )
                .header(HttpHeaders.SET_COOKIE, String.valueOf(
                        cookieProvider.createRefreshTokenCookie(userLoginResponse.refreshToken()))
                )
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(Principal principal) {
        SecurityContextHolder.clearContext();
        userService.logout(principal.getName());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, String.valueOf(
                        cookieProvider.removeToken("accessToken"))
                )
                .header(HttpHeaders.SET_COOKIE, String.valueOf(
                        cookieProvider.removeToken("refreshToken"))
                )
                .build();
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    String username = cookie.getValue();
                    if (username == null) {
                        break;
                    }

                }
            }
        }
        throw new AuthException(ErrorCode.NOT_FOUND);
    }

//    @PostMapping("/signup")
//    public ResponseEntity<Void> login(@RequestPart @Valid UserCreateDto userCreateDto) {
//
//        userService.createUser(userCreateDto);
//
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/find")
//    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
//
//        HttpSession session  = request.getSession(false);
//
//        if (session == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        String username = (String) session.getAttribute("username");
//
//        SiteUserDto siteuSerDto = userService.getUser(username);
//
//
//        return ResponseEntity.ok().body(siteuSerDto);
//    }

//    @GetMapping("/find")
//    public ResponseEntity<?> getUserInfo(Principal principal) {
//
//        SiteUserDto siteuSerDto = userService.getUser(principal.getName());
//
//        return ResponseEntity.ok().body(siteuSerDto);
//    }

}
