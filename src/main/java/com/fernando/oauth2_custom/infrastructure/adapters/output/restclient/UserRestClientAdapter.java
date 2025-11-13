package com.fernando.oauth2_custom.infrastructure.adapters.output.restclient;

import com.fernando.oauth2_custom.application.ports.output.UserRestClientPort;
import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.client.UserFeignClient;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.mappers.UserRestClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRestClientAdapter implements UserRestClientPort {
    private final UserFeignClient userFeignClient;
    private final UserRestClientMapper userRestClientMapper;

    @Override
    public User checkCredential(User user) {
        return userRestClientMapper.checkCredentialResponseToUser(userFeignClient.checkCredentials(userRestClientMapper.authRequestToUser(user)));
    }
}
