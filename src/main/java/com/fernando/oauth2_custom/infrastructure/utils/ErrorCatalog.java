package com.fernando.oauth2_custom.infrastructure.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {
    OATH2_INTERNAL_SERVER_ERROR("SEC_000", "Internal server error."),
    FEIGN_CLIENT_ERROR("SEC_001", "Occurred an error in services."),
    OAUTH2_BAD_PARAMETER("SEC_002","Invalid parameters for creation.");
    private final String code;
    private final String message;
}
