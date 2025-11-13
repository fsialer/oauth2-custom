package com.fernando.oauth2_custom.domain.models;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private Set<String> roles;
//    private String token;
//    private String refreshToken;
//    private String tokenType;
//    private Long expiresIn;
}
