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
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.fernando.oauth2_custom.infrastructure.utils.ErrorCatalog.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfigTest.class)
@WebMvcTest(AuthRestAdapter.class)
class GlobalControllerAdviceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthRestMapper authRestMapper;

    @MockitoBean
    private AuthUserUseCase authUserUseCase;

    @Test
    @DisplayName("Expect MethodArgumentNotValidException When An Attribute Empty")
    void Expect_MethodArgumentNotValidException_When_AnAttributeEmpty()  throws Exception {
        String requestJson = "{\"email\":\"\",\"password\":\"pass123\"}";

        mockMvc.perform(post("/oauth2/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(OAUTH2_BAD_PARAMETER.getCode()))
                .andExpect(jsonPath("$.details[0]").value(org.hamcrest.Matchers.containsString("email: Field email cannot be blank")));
    }

    @Test
    @DisplayName("Expect FeignException When Client CheckCredential Is Disconnect")
    void Expect_FeignException_When_ClientCheckCredentialIsDisconnect() throws Exception{
        TokenResponse tokenResponse = TestUtilUser.mockTokenResponse();
        User user=TestUtilUser.mockUser();
        Mockito.when(authRestMapper.authorizationTokenToTokenResponse(any())).thenReturn(tokenResponse);
        Mockito.when(authRestMapper.loginRequestToUser(any())).thenReturn(user);
        Mockito.when(authUserUseCase.authUser(any())).thenThrow(FeignException.class);
        String requestJson = "{\"email\":\"exmape@mail.com\",\"password\":\"pass123\"}";
        mockMvc.perform(post("/oauth2/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(FEIGN_CLIENT_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(FEIGN_CLIENT_ERROR.getMessage()));
    }

    @Test
    @DisplayName("Expect Exception When An Attribute Empty")
    void Expect_Exception_When_AnAttributeEmpty()  throws Exception {
        TokenResponse tokenResponse = TestUtilUser.mockTokenResponse();
        User user=TestUtilUser.mockUser();
        String requestJson = "{\"email\":\"exmape@mail.com\",\"password\":\"pass123\"}";
        Mockito.when(authRestMapper.authorizationTokenToTokenResponse(any())).thenReturn(tokenResponse);
        Mockito.when(authRestMapper.loginRequestToUser(any())).thenReturn(user);
        Mockito.when(authUserUseCase.authUser(any())).thenThrow(RuntimeException.class);

        mockMvc.perform(post("/oauth2/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(OATH2_INTERNAL_SERVER_ERROR.getCode()));
    }



}
