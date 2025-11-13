package com.fernando.oauth2_custom.infrastructure.adapters.input.rest;

import com.fernando.oauth2_custom.application.ports.input.AuthUserUseCase;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.mappers.AuthRestMapper;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.requests.LoginRequest;
import com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.responses.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class AuthRestAdapter {
    private final AuthUserUseCase authUserUseCase;
    private final AuthRestMapper authRestMapper;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authRestMapper.authorizationTokenToTokenResponse(authUserUseCase.authUser(authRestMapper.loginRequestToUser(request))));
    }
}
