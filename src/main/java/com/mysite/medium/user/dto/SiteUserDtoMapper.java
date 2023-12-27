package com.mysite.medium.user.dto;

import com.mysite.medium.user.entity.SiteUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteUserDtoMapper {
    SiteUser siteUserDtoToSiteUser(SiteUserDto siteUserDto);
    SiteUserDto siteUserToSiteUserDto(SiteUser siteUser);
}
