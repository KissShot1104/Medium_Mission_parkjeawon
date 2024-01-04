package com.mysite.medium.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Builder
public class MemberDto {


    private Long id;

    private String username;

    private String password;

    private String email;

    private Boolean isPaid;

    @QueryProjection
    public MemberDto(String username, Boolean isPaid) {
        this.username = username;
        this.isPaid = isPaid;
    }
}
