package com.fernando.oauth2_custom.utils;

import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.requests.LoginRequest;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.responses.TokenResponse;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.requests.AuthRequest;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.responses.CheckCredentialResponse;

import java.util.Set;

public class TestUtilUser {
    public static User mockUser(){
        return User.builder()
                .id(1L)
                .email("example@mail.com")
                .password("testing")
                .roles(Set.of("USER"))
                .fullName("John Doe")
                .build();
    }


    public static AuthRequest mockAuthRequest(){
        return AuthRequest.builder()
                .email("example@mail.com")
                .password("testing")
                .build();
    }

    public static CheckCredentialResponse mockCheckCredentialResponse(){
        return CheckCredentialResponse.builder()
                .id(1L)
                .email("example@mail.com")
                .roles(Set.of("USER"))
                .fullName("John Doe")
                .build();
    }

    public static TokenResponse mockTokenResponse(){
        return TokenResponse.builder()
                .accessToken("ebyds878dsd5plfdf744f.odf44df")
                .refreshToken("ebzx8d445sd36sd5sd")
                .tokenType("Bearer")
                .expiresIn(18696585L)
                .build();
    }

    public static LoginRequest mockLoginRequest(){
        return LoginRequest.builder()
                .email("exmaps6@mail.com")
                .password("ebzx8d445sd36sd5sd")
                .build();
    }

    public static AuthorizationToken mockAuthorizationToken(){
        return AuthorizationToken.builder()
                .token("ebyds878dsd5plfdf744f.odf44df")
                .tokenType("Bearer")
                .expiresIn(176957455L)
                .refreshToken("ebzx8d445sd36sd5sd")
                .build();
    }







}
