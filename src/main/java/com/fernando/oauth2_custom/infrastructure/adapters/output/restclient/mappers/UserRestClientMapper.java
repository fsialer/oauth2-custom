package com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.mappers;

import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.requests.AuthRequest;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.responses.CheckCredentialResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestClientMapper {
    default AuthRequest authRequestToUser(User user){
        return AuthRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    default User checkCredentialResponseToUser(CheckCredentialResponse checkCredentialResponse){
        return User.builder()
                .id(checkCredentialResponse.id())
                .email(checkCredentialResponse.email())
                .fullName(checkCredentialResponse.fullName())
                .roles(checkCredentialResponse.roles())
                .build();
    }
}
