package com.example.vinyl.vibes.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

public class JwtTool {

    public static String generateToken(String userId, long expirationTime) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey() {
        String secretKey = "a3VyY2luYWt1cmNpbmFrdXJjaW5ha3VyY2luYWt1cmNpbmFrdXJjaW5ha3VyY2luYWt1cmNpbmFrdXJjaW5ha3VyY2luYWt1cmNpbmE=";
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        System.out.println(Arrays.toString(Decoders.BASE64.decode(secretKey)));
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
