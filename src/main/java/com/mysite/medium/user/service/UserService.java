package com.mysite.medium.user.service;

import com.mysite.medium.user.dto.SiteUserDto;
import com.mysite.medium.user.dto.UserCreateDto;
import com.mysite.medium.user.entity.SiteUser;

public interface UserService {

    public void createUser(UserCreateDto userCreateDto);

    public SiteUserDto getUser(String username);

    public SiteUser siteUserFormToSiteUser(SiteUserDto siteUserDto);

    public SiteUserDto siteUserToSiteUserForm(SiteUser siteUser);
}
