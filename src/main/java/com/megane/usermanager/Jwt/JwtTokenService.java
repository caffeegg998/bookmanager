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
        Date exp = new Date(now.getTime() + 10 * 60 * 1000);
        String accessToken = Jwts.builder().setClaims(claims).setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return accessToken;
    }
//    public String refreshToken(String username, List<String> authority){
//        Claims claims = Jwts.claims().setSubject(username);
//        claims.put("authorities", authority);
//        Date now = new Date();
//        Date exp = new Date(now.getTime() + 5 * 60 * 1000);
//
//        String refreshToken = Jwts.builder().setSubject(username).setIssuedAt(now)
//                .setExpiration(exp)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//        return refreshToken;
//    }


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

    //Refresh Token
    // Phương thức trợ giúp để tạo ra một refresh token
    public String createRefreshToken(String username, List<String> authority){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities", authority);
        Date now = new Date();
        Date exp = new Date(now.getTime() + 20 * 60 * 1000); // Đặt thời gian hết hạn cho refresh token mới

        String refreshToken = Jwts.builder().setClaims(claims).setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return refreshToken;
    }

    public String refreshAccessToken(String refreshToken){
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody();
            String username = claims.getSubject();
            List<String> authority = (List<String>) claims.get("authorities");

            // Tạo một access token mới
            String accessToken = createToken(username, authority);

            return accessToken;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
