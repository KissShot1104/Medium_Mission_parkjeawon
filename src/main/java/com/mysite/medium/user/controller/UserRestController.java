package com.mysite.medium.user.controller;

import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> login(@RequestPart @Valid UserCreateDto userCreateDto) {

        userService.createUser(userCreateDto);

        return ResponseEntity.ok().build();
    }
}
