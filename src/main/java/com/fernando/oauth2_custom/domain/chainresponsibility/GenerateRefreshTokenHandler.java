package com.fernando.oauth2_custom.domain.chainresponsibility;

import com.fernando.oauth2_custom.domain.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenerateRefreshTokenHandler implements AuthUserHandler{
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
        String refreshToken = generateRefreshToken(context.getStoredUser());

        context.getStoredAuthorizationToken().setRefreshToken(refreshToken);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }

    private String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("type", "refresh");
        return createRefreshToken(claims, user.getEmail());
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpiration);

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
