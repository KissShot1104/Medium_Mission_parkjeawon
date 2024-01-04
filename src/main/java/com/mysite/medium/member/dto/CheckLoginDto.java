package com.mysite.medium.member.dto;


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
