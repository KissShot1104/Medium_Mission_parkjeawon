package com.mysite.medium.user.controller;

import com.mysite.medium.global.exception.AuthException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.user.dto.CheckLoginDto;
import com.mysite.medium.user.dto.MemberCreateDto;
import com.mysite.medium.user.service.MemberService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberRestController {
    private final MemberService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> login(@RequestPart @Valid MemberCreateDto memberCreateDto) {

        userService.createUser(memberCreateDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkUser(Principal principal) {

        if (principal == null) {
            throw new AuthException(ErrorCode.IS_NOT_LOGIN);
        }

        CheckLoginDto checkLoginDto = userService.checkLogin(principal.getName());

        return ResponseEntity.ok().body(checkLoginDto);
    }

}
