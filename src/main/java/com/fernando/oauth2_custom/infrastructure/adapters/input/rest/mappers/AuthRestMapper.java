package com.fernando.oauth2_custom.infrastructure.adapters.input.rest.mappers;

import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.requests.LoginRequest;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.responses.TokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthRestMapper {
    default User loginRequestToUser(LoginRequest loginRequest){
        return User.builder()
                .email(loginRequest.getEmail())
                .password(loginRequest.getPassword())
                .build();
    }

    default TokenResponse authorizationTokenToTokenResponse(AuthorizationToken user){
        return TokenResponse.builder()
                .accessToken(user.getToken())
                .refreshToken(user.getRefreshToken())
                .tokenType(user.getTokenType())
                .expiresIn(user.getExpiresIn())
                .build();
    }


}
