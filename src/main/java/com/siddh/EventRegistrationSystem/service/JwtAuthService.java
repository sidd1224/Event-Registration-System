package com.siddh.EventRegistrationSystem.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtAuthService {

    private final SecretKey secretKey= Keys.hmacShaKeyFor("shdvilugvks;usbcuo'bs c'oscsjabci;abkjcskchdvosdvi".getBytes());
    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(secretKey)
                .compact();
    }
    public String extractUsername(String token){
        Claims claims= Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
    public boolean validateToken(String token){
        try{
            Claims claims=Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
                    return true;
        }catch(Exception e){
            return false;

        }

    }


}
