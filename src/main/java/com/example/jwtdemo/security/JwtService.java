package com.example.jwtdemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + MINUTES.toMillis(expiration)))
                .signWith(getPublicSigningKey())
                .compact();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration).before(new Date());
    }

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getPublicSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    private SecretKey getPublicSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
