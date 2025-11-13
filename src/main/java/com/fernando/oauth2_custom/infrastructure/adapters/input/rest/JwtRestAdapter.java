package com.fernando.oauth2_custom.infrastructure.adapters.input.rest;

import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtRestAdapter {

    private final JWKSource<SecurityContext> jwkSource;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        return ((ImmutableJWKSet<SecurityContext>) jwkSource).getJWKSet().toJSONObject();
    }
}
