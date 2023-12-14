package com.mysite.medium.user.service;

import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.repository.UserRepository;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.medium.DataNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserCreateDto userCreateDto) {

        SiteUser user = SiteUser.builder()
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword1()))
                .build();

        userRepository.save(user);
    }
    
    public SiteUserDto getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);

        if (siteUser.isEmpty()) {
            throw new DataNotFoundException("siteuser not found");
        }

        return SiteUserDto.builder()
                .id(siteUser.get().getId())
                .password(siteUser.get().getPassword())
                .username(siteUser.get().getUsername())
                .email(siteUser.get().getEmail())
                .build();
    }

    public SiteUser siteUserFormToSiteUser(SiteUserDto siteUserDto) {
        return SiteUser.builder()
                .id(siteUserDto.getId())
                .username(siteUserDto.getUsername())
                .password(siteUserDto.getPassword())
                .email(siteUserDto.getEmail())
                .build();
    }

    public SiteUserDto siteUserToSiteUserForm(SiteUser siteUser) {
        return SiteUserDto.builder()
                .id(siteUser.getId())
                .username(siteUser.getUsername())
                .password(siteUser.getPassword())
                .email(siteUser.getEmail())
                .build();
    }

}