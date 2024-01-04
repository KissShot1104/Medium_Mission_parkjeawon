package com.mysite.medium.user.service;

import com.mysite.medium.DataNotFoundException;
import com.mysite.medium.global.exception.AuthException;
import com.mysite.medium.global.exception.ErrorCode;
import com.mysite.medium.user.dto.CheckLoginDto;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.SiteUserDtoMapper;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SiteUserDtoMapper siteUserDtoMapper;

    @Transactional
    public void createUser(final UserCreateDto userCreateDto) {

        Optional<SiteUser> duplicatedLoginId = userRepository.findByUsername(userCreateDto.getUsername());
        if (duplicatedLoginId.isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
        Optional<SiteUser> duplicatedEmail = userRepository.findByEmail(userCreateDto.getEmail());
        if (duplicatedEmail.isPresent()) {
            throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
        }

        userCreateDto.checkEqualsPassword();

        final SiteUser user = SiteUser.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword1()))
                .isPaid(userCreateDto.getIsPaid())
                .build();

        userRepository.save(user);
    }

    public CheckLoginDto checkLogin(final String username) {
        final SiteUser siteUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(ErrorCode.IS_NOT_LOGIN));

        final CheckLoginDto siteUserDto = siteUserDtoMapper.siteUserToCheckLoginDto(siteUser);

        return siteUserDto;
    }



}