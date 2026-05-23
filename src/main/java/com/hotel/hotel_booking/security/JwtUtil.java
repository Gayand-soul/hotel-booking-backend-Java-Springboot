package com.hotel.hotel_booking.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.security.Key;

@Component
public class JwtUtil {

    //Secret key-must be at least 256bits for HS256
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    //Token valid for 24 hours
    private final long EXPIRATION_MS =  1000 * 60 * 60 * 24;

    //Generate Token
    public String generateToken (String username, String role){
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(secretKey)
                .compact();
    }

    //Extract username from token
    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //Validate token
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
