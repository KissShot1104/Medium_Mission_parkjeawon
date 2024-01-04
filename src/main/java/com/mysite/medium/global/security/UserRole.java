package com.mysite.medium.global.security;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    PAID("ROLE_PAID");

    UserRole(String role) {
        this.role = role;
    }

    private String role;
}