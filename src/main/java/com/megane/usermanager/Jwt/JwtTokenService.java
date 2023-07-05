package com.megane.usermanager.Jwt;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {
    @Value("${jwt.secret:123}")
    private String secretKey;

    private long validity = 5;

    public String createToken(String username, List<String> authority){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities", authority);
        Date now = new Date();
        Date exp = new Date(now.getTime() + validity * 60 * 1000);

        return Jwts.builder().setClaims(claims).setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean inValidToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            //ko lam gi
        }
        return false;
    }

    public String getUsername(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getSubject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
