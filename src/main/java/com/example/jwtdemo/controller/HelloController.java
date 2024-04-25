package com.example.jwtdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping
    public ResponseEntity<String> getHelloUser() {
        return ResponseEntity.ok("Hello user from secured");
    }

    @Secured("ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<String> getHelloAdmin() {
        return ResponseEntity.ok("Hello admin from secured");
    }

}
