package com.recruva.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recruva.db.entities.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {
    
    @Value ("${jwt.secret}")
    private String secretKey;

    @Value ("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        // Convert the secret key string to a SecretKey object
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        // Implementation for generating a JWT token using the secret key and expiration time

        return Jwts.builder().subject(user.getId().toString()).issuedAt(new Date()).signWith(getSigningKey()).expiration(new Date(System.currentTimeMillis() + expiration)).compact();
    }

    public UUID extractUserIdFromToken(String token) {
        // Implementation for extracting the user ID from the JWT token
        String userId = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
        return UUID.fromString(userId);
    }

    public boolean isTokenValid(String token) {
        // Implementation for validating the JWT token
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
