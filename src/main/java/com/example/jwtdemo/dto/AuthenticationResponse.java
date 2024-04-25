package com.example.jwtdemo.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticationResponse {
    String email;
    String token;
}
