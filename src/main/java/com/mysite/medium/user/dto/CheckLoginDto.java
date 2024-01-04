package com.mysite.medium.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CheckLoginDto {
    private String username;
    private Boolean isPaid;
}
