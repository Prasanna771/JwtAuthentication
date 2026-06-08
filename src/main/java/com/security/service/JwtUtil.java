package com.security.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil 
{

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey123456";

    private SecretKey getSignKey()
    {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Generate JWT Token
    public String generateToken(String email)
    {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis() + 60000
                        )
                )
                .signWith(getSignKey())
                .compact();
    }

    // Extract all claims
    public Claims extractAllClaims(String token)
    {

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract username(email)
    public String extractUsername(String token) 
    {

        return extractAllClaims(token)
                .getSubject();
    }

    // Check expiry
    public boolean isTokenExpired(String token)
    {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // Validate token
    public boolean validateToken(String token)
    {

        try
        {

            return !isTokenExpired(token);

        }
        catch (Exception e) 
        {

            return false;
        }
    }
    
    
    public String generateRefreshToken(String email)
    {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                    new Date(
                        System.currentTimeMillis()
                        + 86400000
                    )
                )
                .signWith(getSignKey())
                .compact();
    }
}