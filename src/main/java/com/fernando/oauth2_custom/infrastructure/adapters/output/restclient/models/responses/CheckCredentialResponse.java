package com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.responses;

import lombok.Builder;

import java.util.Set;

@Builder
public record CheckCredentialResponse(Long id, String email, String fullName, Set<String> roles) {
}
