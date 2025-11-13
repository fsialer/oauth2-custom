package com.fernando.oauth2_custom.domain.chainresponsibility;

import com.fernando.oauth2_custom.application.ports.output.UserRestClientPort;
import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.utils.TestUtilUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckCredentialHandlerTest {
    private CheckCredentialHandler handler;

    @Mock
    private AuthUserHandler nextHandler;

    @Mock
    private AuthorizationToken authorizationToken;

    @Mock
    private UserRestClientPort userRestClientPort;

    @Mock
    private User user;

    private AuthContext context;

    @BeforeEach
    void setUp() {
        handler = new CheckCredentialHandler(userRestClientPort);
        context = new AuthContext();
        context.setStoredAuthorizationToken(authorizationToken);
        context.setUser(user);
        context.setStoredUser(user);
    }

    @Test
    @DisplayName("When User Is Correct Expect NextHandle")
    void When_UserIsCorrect_Expect_NextHandle(){
        user= TestUtilUser.mockUser();
        when(userRestClientPort.checkCredential(any())).thenReturn(user);
        handler.setNext(nextHandler);
        handler.handle(context);
        verify(nextHandler).handle(context);
    }

    @Test
    @DisplayName("Expect Not Call NextHandler When HandleNext Is Null")
    void Expect_NotCallNextHandler_When_HandleNextIsNull() {
        user= TestUtilUser.mockUser();
        when(userRestClientPort.checkCredential(any())).thenReturn(user);
        assertDoesNotThrow(() -> handler.handle(context));
    }
}
