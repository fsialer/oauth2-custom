package com.fernando.oauth2_custom.domain.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizationToken {
    private String token;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
}
