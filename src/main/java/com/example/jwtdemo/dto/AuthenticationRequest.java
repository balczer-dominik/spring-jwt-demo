package com.example.jwtdemo.dto;

import lombok.Value;

@Value
public class AuthenticationRequest {
    String email;
    String password;
}
