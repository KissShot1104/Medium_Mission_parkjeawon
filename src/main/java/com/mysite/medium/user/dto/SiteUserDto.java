package com.mysite.medium.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteUserDto {


    private Long id;

    private String username;

    private String password;

    private String email;

    private Boolean isPaid;

    @QueryProjection
    public SiteUserDto(String username, Boolean isPaid) {
        this.username = username;
        this.isPaid = isPaid;
    }
}
