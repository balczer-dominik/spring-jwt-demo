package com.example.jwtdemo.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Role {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
}
