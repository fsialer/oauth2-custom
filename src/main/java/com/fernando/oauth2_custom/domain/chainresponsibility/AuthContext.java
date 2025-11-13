package com.fernando.oauth2_custom.domain.chainresponsibility;

import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthContext {
    private User user;
    private User storedUser;
    private AuthorizationToken storedAuthorizationToken;
}
