package com.example.jwtdemo.dto;

import lombok.Value;

@Value
public class RegisterRequest {
    String email;
    String password;
    String firstName;
    String lastName;
}
