package com.fernando.oauth2_custom.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.oauth2_custom.application.ports.input.AuthUserUseCase;
import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.mappers.AuthRestMapper;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.requests.LoginRequest;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.responses.TokenResponse;
import com.fernando.oauth2_custom.infrastructure.config.SecurityConfigTest;
import com.fernando.oauth2_custom.utils.TestUtilUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfigTest.class)
@WebMvcTest(AuthRestAdapter.class)
class AuthRestAdapterTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthRestMapper authRestMapper;

    @MockitoBean
    private AuthUserUseCase authUserUseCase;

    @Test
    @DisplayName("When Credentials Are Correct Expect Return200")
    void When_CredentialsAreCorrect_Expect_Return200() throws Exception {
        TokenResponse tokenResponse = TestUtilUser.mockTokenResponse();
        AuthorizationToken authorizationToken=TestUtilUser.mockAuthorizationToken();
        User user=TestUtilUser.mockUser();
        LoginRequest loginRequest=TestUtilUser.mockLoginRequest();
        Mockito.when(authRestMapper.authorizationTokenToTokenResponse(any())).thenReturn(tokenResponse);
        Mockito.when(authRestMapper.loginRequestToUser(any())).thenReturn(user);
        Mockito.when(authUserUseCase.authUser(any())).thenReturn(authorizationToken);

        mockMvc.perform(post("/oauth2/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(tokenResponse.accessToken()))
        .andExpect(jsonPath("$.refreshToken").value(tokenResponse.refreshToken()))
        .andExpect(jsonPath("$.tokenType").value(tokenResponse.tokenType()))
        .andExpect(jsonPath("$.expiresIn").value(tokenResponse.expiresIn()));
        Mockito.verify(authRestMapper).authorizationTokenToTokenResponse(any());
        Mockito.verify(authRestMapper).loginRequestToUser(any());
        Mockito.verify(authUserUseCase).authUser(any());
    }
}
