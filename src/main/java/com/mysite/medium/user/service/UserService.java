package com.mysite.medium.user.service;


import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.dto.UserLoginResponse;
import java.util.List;


public interface UserService {

    void createUser(final UserCreateDto userCreateDto);

    SiteUserDto getUser(final String username);

    void logout(String memberId);
    UserLoginResponse login(String username, String password);
    List<String> getRoles(String memberId);
}
