package com.fernando.oauth2_custom.domain.chainresponsibility;

public interface AuthUserHandler {
    AuthUserHandler setNext(AuthUserHandler handler);
    void handle(AuthContext context);
}
