package com.fernando.oauth2_custom.domain.chainresponsibility;

import com.fernando.oauth2_custom.domain.models.AuthorizationToken;
import com.fernando.oauth2_custom.domain.models.User;
import com.fernando.oauth2_custom.utils.TestUtilAuthorizationToken;
import com.fernando.oauth2_custom.utils.TestUtilUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GenerateTokenHandlerTest {
    @InjectMocks
    private GenerateTokenHandler handler;

    @Mock
    private AuthUserHandler nextHandler;

    private AuthContext context;

    @Mock
    private AuthorizationToken authorizationToken;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        handler = new GenerateTokenHandler();
        ReflectionTestUtils.setField(handler, "secret", "mySecretKey123456789012345678901234567890");
        ReflectionTestUtils.setField(handler, "refreshExpiration", 604800000L);
        ReflectionTestUtils.setField(handler, "expiration", 604800000L);
        context = new AuthContext();
        context.setStoredAuthorizationToken(authorizationToken);
        context.setUser(user);
        context.setStoredUser(user);
    }

    @Test
    @DisplayName("When User Was Check Credential Expect Generate Token And A Handle")
    void When_UserWasCheckCredential_Expect_GenerateTokenAndAHandle() {
        // Arrange
        context.setStoredUser(TestUtilUser.mockUser());
        context.setUser(TestUtilUser.mockUser());
        context.setStoredAuthorizationToken(TestUtilAuthorizationToken.mockAuthorizatonToken());
        // Act
        handler.setNext(nextHandler);
        handler.handle(context);

        // Assert
        assertNotNull(context.getStoredAuthorizationToken().getToken());
        assertFalse(context.getStoredAuthorizationToken().getToken().isEmpty());
    }


    @Test
    @DisplayName("Expect Not Call NextHandler When HandleNext Is Null")
    void Expect_NotCallNextHandler_When_HandleNextIsNull() {
        // Arrange
        context.setStoredUser(TestUtilUser.mockUser());
        context.setUser(TestUtilUser.mockUser());
        context.setStoredAuthorizationToken(TestUtilAuthorizationToken.mockAuthorizatonToken());
        assertDoesNotThrow(() -> handler.handle(context));
        // Assert
        assertNotNull(context.getStoredAuthorizationToken().getToken());
        assertFalse(context.getStoredAuthorizationToken().getToken().isEmpty());
    }

}
