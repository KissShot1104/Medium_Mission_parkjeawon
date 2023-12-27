package com.mysite.medium.user.dto;

import com.mysite.medium.user.dto.SiteUserDto.SiteUserDtoBuilder;
import com.mysite.medium.user.entity.SiteUser;
import com.mysite.medium.user.entity.SiteUser.SiteUserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-27T14:47:38+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.1 (Azul Systems, Inc.)"
)
@Component
public class SiteUserDtoMapperImpl implements SiteUserDtoMapper {

    @Override
    public SiteUser siteUserDtoToSiteUser(SiteUserDto siteUserDto) {
        if ( siteUserDto == null ) {
            return null;
        }

        SiteUserBuilder siteUser = SiteUser.builder();

        siteUser.id( siteUserDto.getId() );
        siteUser.username( siteUserDto.getUsername() );
        siteUser.password( siteUserDto.getPassword() );
        siteUser.email( siteUserDto.getEmail() );

        return siteUser.build();
    }

    @Override
    public SiteUserDto siteUserToSiteUserDto(SiteUser siteUser) {
        if ( siteUser == null ) {
            return null;
        }

        SiteUserDtoBuilder siteUserDto = SiteUserDto.builder();

        siteUserDto.id( siteUser.getId() );
        siteUserDto.username( siteUser.getUsername() );
        siteUserDto.password( siteUser.getPassword() );
        siteUserDto.email( siteUser.getEmail() );

        return siteUserDto.build();
    }
}
