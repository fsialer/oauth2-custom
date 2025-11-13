package com.fernando.oauth2_custom.application.ports.input;

import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;

public interface AuthUserUseCase {
    AuthorizationToken authUser(User user);
}
