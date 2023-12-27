package com.mysite.medium.user.service;

import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.entity.SiteUser;

public interface UserService {

    void createUser(final UserCreateDto userCreateDto);

    SiteUserDto getUser(final String username);

}
