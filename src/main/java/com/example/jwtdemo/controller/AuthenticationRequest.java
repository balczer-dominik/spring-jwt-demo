package com.example.jwtdemo.controller;

import lombok.Value;

@Value
public class AuthenticationRequest {
    String email;
    String password;
}
