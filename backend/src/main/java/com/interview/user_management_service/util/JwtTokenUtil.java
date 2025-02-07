package com.interview.user_management_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String generateToken(String username, String role) {
        try {
            String token = Jwts.builder()
                    .setSubject(username)
                    .claim("authorities", Collections.singletonList("ROLE_" + role))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(SignatureAlgorithm.HS256, SecretKeyUtil.createSecretKeyFromString(secretKey))
                    .compact();
            //log.info("Generated token: {}", token);
            return token;
        }catch (Exception e) {
            log.warn("Error generating token", e);
            throw e;
        }
    }
}