package com.fernando.oauth2_custom.domain.chainresponsibility;

import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenerateTokenHandler implements AuthUserHandler {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private AuthUserHandler nextHandler;

    @Override
    public AuthUserHandler setNext(AuthUserHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    @Override
    public void handle(AuthContext context) {
        String token = generateToken(context.getStoredUser());
        AuthorizationToken authorizationToken=AuthorizationToken.builder()
                .token(token)
                .expiresIn(expiration / 1000)
                .tokenType("Bearer")
                .build();
        context.setStoredAuthorizationToken(authorizationToken);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    private String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("fullName", user.getFullName());
        claims.put("roles", user.getRoles());
        return createToken(claims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

}
