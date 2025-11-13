package com.fernando.oauth2_custom.domain.chainresponsibility;

import com.fernando.oauth2_custom.application.ports.output.UserRestClientPort;
import com.fernando.oauth2_custom.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckCredentialHandler implements AuthUserHandler{
    private final UserRestClientPort userRestClientPort;
    private AuthUserHandler nextHandler;

    @Override
    public AuthUserHandler setNext(AuthUserHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    @Override
    public void handle(AuthContext context) {
        User user= userRestClientPort.checkCredential(context.getUser());
        context.setStoredUser(user);
        if (nextHandler != null) {
            nextHandler.handle(context);
        }
    }
}
