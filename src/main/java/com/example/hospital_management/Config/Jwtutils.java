package com.example.hospital_management.Config;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Jwtutils {


    private final String Secret = "76b4dcb47ca25b2a69e7c69430a885511021686a9baa25ee3ce71bdc1270faa7";
    private final long Exp=1000*60*60;

    public String generateToken(String email,String role)
    {
        String token = Jwts.builder()
        .setSubject(email)
        .claim("role", role) 
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+Exp))
        .signWith(SignatureAlgorithm.HS256,Secret)
        .compact();
        return token;
    }  


public String extractUserName(String token)
{

    String extractedUserName= Jwts.parserBuilder()
    .setSigningKey(Secret)
    .build()
    .parseClaimsJws(token)
    .getBody()
    .getSubject();
    return extractedUserName;
}

public boolean extractExpiration(String token)
{
   boolean tokenExpiration = Jwts.parserBuilder()
   .setSigningKey(Secret)
   .build()
   .parseClaimsJws(token)
   .getBody()
   .getExpiration()
   .before(new Date());
   return tokenExpiration;
}
public boolean isTokenExpired(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Secret)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());
}

public boolean validateToken(String token) {
    try {
        String username = extractUserName(token);
        return (username != null && !isTokenExpired(token));
    } catch (Exception e) {
        return false;
    }
}

}
