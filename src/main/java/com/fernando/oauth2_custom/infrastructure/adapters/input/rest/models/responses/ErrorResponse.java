package com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.responses;

import com.fernando.oauth2_custom.domain.enums.ErrorType;
import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponse(String code, ErrorType type, String message, List<String> details, String timestamp) {
}
