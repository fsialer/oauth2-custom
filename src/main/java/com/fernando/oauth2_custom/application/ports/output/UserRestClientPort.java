package com.fernando.oauth2_custom.application.ports.output;

import com.fernando.oauth2_custom.domain.models.User;

public interface UserRestClientPort {
    User checkCredential(User user);
}
