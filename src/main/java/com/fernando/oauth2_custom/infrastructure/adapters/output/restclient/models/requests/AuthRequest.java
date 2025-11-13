package com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.requests;

import lombok.Builder;

@Builder
public record AuthRequest(String email,String password){}
