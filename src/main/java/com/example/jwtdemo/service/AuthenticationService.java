package com.example.jwtdemo.service;

import com.example.jwtdemo.domain.User;
import com.example.jwtdemo.dto.AuthenticationRequest;
import com.example.jwtdemo.dto.AuthenticationResponse;
import com.example.jwtdemo.dto.RegisterRequest;
import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.jwtdemo.domain.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(USER)
                .build();

        userRepository.save(user);

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .token(jwtService.generateToken(user))
                .build();
    }
}
