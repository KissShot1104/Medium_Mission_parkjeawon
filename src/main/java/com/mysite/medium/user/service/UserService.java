package com.mysite.medium.user.service;

import com.mysite.medium.user.dto.CheckLoginDto;
import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.entity.SiteUser;

public interface UserService {

    void createUser(final UserCreateDto userCreateDto);

    CheckLoginDto checkLogin(final String username);

}
