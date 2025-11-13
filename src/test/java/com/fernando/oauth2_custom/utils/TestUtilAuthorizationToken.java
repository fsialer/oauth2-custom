package com.fernando.oauth2_custom.utils;

import com.fernando.oauth2_custom.domain.models.AuthorizationToken;

public class TestUtilAuthorizationToken {
    public static AuthorizationToken mockAuthorizatonToken(){
        return AuthorizationToken.builder()
                .token("erewfs54s574ds41d54sdsdawv.fkshsd.dsddds")
                .refreshToken("s74sas1025sa4s.s5s874as")
                .tokenType("Bearer")
                .expiresIn(14521996L)
                .build();
    }
}
