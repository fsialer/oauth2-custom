package com.fernando.oauth2_custom.infrastructure.adapters.output.restclient;

import com.fernando.oauth2_custom.application.ports.output.UserRestClientPort;
import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.client.UserFeignClient;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.mappers.UserRestClientMapper;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.requests.AuthRequest;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.responses.CheckCredentialResponse;
import com.fernando.oauth2_custom.utils.TestUtilUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRestClientAdapterTest {
    @Mock
    private UserFeignClient userFeignClient;
    @Mock
    private UserRestClientPort userRestClientPort;

    @Mock
    private UserRestClientMapper userRestClientMapper;

    @InjectMocks
    private UserRestClientAdapter userRestClientAdapter;

    @Test
    @DisplayName("When Service CheckCredentials Is Availability Expect Information User Validated")
    void When_ServiceCheckCredentialsIsAvailability_Expect_InformationUserValidated(){
        User user= TestUtilUser.mockUser();
        AuthRequest authRequest=TestUtilUser.mockAuthRequest();
        CheckCredentialResponse checkCredentialResponse=TestUtilUser.mockCheckCredentialResponse();
        when(userFeignClient.checkCredentials(any())).thenReturn(checkCredentialResponse);
        when(userRestClientMapper.authRequestToUser(any())).thenReturn(authRequest);
        when(userRestClientMapper.checkCredentialResponseToUser(any())).thenReturn(user);

        User response=userRestClientAdapter.checkCredential(user);

        Assertions.assertEquals(response.getEmail(), user.getEmail());
        Assertions.assertEquals(response.getFullName(), user.getFullName());
        Assertions.assertEquals(response.getId(), user.getId());
        Assertions.assertEquals(response.getRoles(), user.getRoles());
        verify(userFeignClient,times(1)).checkCredentials(authRequest);
        verify(userRestClientMapper,times(1)).authRequestToUser(any());
        verify(userRestClientMapper,times(1)).checkCredentialResponseToUser(any());
    }
}
