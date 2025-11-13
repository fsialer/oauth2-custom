package com.fernando.oauth2_custom.domain.services;

import com.fernando.oauth2_custom.domain.chainresponsibility.AuthContext;
import com.fernando.oauth2_custom.domain.chainresponsibility.CheckCredentialHandler;
import com.fernando.oauth2_custom.domain.chainresponsibility.GenerateRefreshTokenHandler;
import com.fernando.oauth2_custom.domain.chainresponsibility.GenerateTokenHandler;
import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.utils.TestUtilAuthorizationToken;
import com.fernando.oauth2_custom.utils.TestUtilUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {
    @Mock
    private CheckCredentialHandler checkCredentialHandler;

    @Mock
    private GenerateTokenHandler generateTokenHandler;

    @Mock
    private GenerateRefreshTokenHandler generateRefreshTokenHandler;

    @InjectMocks
    private AuthorizationService authService;

    @Test
    @DisplayName("When Credentials User Are Correct Expect Generate Token Authorized")
    void When_CredentialsUserAreCorrect_Expect_GenerateTokenAuthorized(){
        // Arrange
        User user = TestUtilUser.mockUser();
        AuthorizationToken mockToken = TestUtilAuthorizationToken.mockAuthorizatonToken();

        when(checkCredentialHandler.setNext(any())).thenReturn(generateTokenHandler);
        when(generateTokenHandler.setNext(any())).thenReturn(generateRefreshTokenHandler);
        
        doAnswer(invocation -> {
            AuthContext context = invocation.getArgument(0);
            context.setStoredAuthorizationToken(mockToken);
            return null;
        }).when(checkCredentialHandler).handle(any(AuthContext.class));

        // Act
        AuthorizationToken result = authService.authUser(user);
        System.out.println(result);
        // Assert
        assertNotNull(result);
        assertEquals(result.getToken(),mockToken.getToken());
        assertEquals(result.getTokenType(),mockToken.getTokenType());
        assertEquals(result.getRefreshToken(),mockToken.getRefreshToken());
        assertEquals(result.getExpiresIn(),mockToken.getExpiresIn());
        verify(checkCredentialHandler, times(1)).setNext(generateTokenHandler);
        verify(generateTokenHandler, times(1)).setNext(generateRefreshTokenHandler);
        verify(checkCredentialHandler, times(1)).handle(any(AuthContext.class));
    }
}
