package com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.responses;

import lombok.Builder;

@Builder
public record TokenResponse(String accessToken,String refreshToken,String tokenType,Long expiresIn) {
}