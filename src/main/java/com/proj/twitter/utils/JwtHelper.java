package com.proj.twitter.utils;

import com.proj.twitter.dto.JwtUser;
import com.proj.twitter.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


public class JwtHelper {
    private static final String JWT_SECRET = "$uper1234asdsjhbasdb4sdx6asd6sa46da6acbasiudasjdlkasndlnasdnasndkjasndknaskdnaslkdkjasbdhlbaskjdbaskjdbas"; // Replace with your actual secret key

    public static String generateTokenForUser(User user) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(user.getId())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET);

        return jwtBuilder.compact();
    }

    public static JwtUser decodeToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            JwtUser jwtUser = new JwtUser();
            jwtUser.setId(claims.getId());
            jwtUser.setEmail(claims.getSubject());

            return jwtUser;
        } catch (Exception e) {
            System.out.println("exp "+e.getMessage());
            return null;
        }
    }
}
