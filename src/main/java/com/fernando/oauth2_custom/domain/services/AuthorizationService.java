package com.fernando.oauth2_custom.domain.services;

import com.fernando.oauth2_custom.application.ports.input.AuthUserUseCase;
import com.fernando.oauth2_custom.domain.chainresponsibility.AuthContext;
import com.fernando.oauth2_custom.domain.chainresponsibility.CheckCredentialHandler;
import com.fernando.oauth2_custom.domain.chainresponsibility.GenerateRefreshTokenHandler;
import com.fernando.oauth2_custom.domain.chainresponsibility.GenerateTokenHandler;
import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements AuthUserUseCase {

    private final CheckCredentialHandler checkCredentialHandler;
    private final GenerateTokenHandler generateTokenHandler;
    private final GenerateRefreshTokenHandler generateRefreshTokenHandler;
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    public AuthorizationToken authUser(User user) {
        AuthContext authContext = AuthContext.builder().user(user).build();
        checkCredentialHandler.setNext(generateTokenHandler)
                .setNext(generateRefreshTokenHandler);
        checkCredentialHandler.handle(authContext);
        return authContext.getStoredAuthorizationToken();
    }
}
