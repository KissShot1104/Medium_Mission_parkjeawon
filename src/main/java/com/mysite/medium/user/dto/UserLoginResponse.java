package com.mysite.medium.user.dto;

import lombok.Builder;

@Builder
public record UserLoginResponse(String accessToken, String refreshToken){

}
