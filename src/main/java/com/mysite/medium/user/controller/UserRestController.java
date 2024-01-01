package com.mysite.medium.user.controller;

import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/find")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {

        HttpSession session  = request.getSession(false);

        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = (String) session.getAttribute("username");

        SiteUserDto siteuSerDto = userService.getUser(username);


        return ResponseEntity.ok().body(siteuSerDto);
    }


//    @GetMapping("/find")
//    public ResponseEntity<?> getUserInfo(Principal principal) {
//
//        SiteUserDto siteuSerDto = userService.getUser(principal.getName());
//
//        return ResponseEntity.ok().body(siteuSerDto);
//    }

}
