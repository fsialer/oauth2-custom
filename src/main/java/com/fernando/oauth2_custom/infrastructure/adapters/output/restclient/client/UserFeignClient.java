package com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.client;

import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.requests.AuthRequest;
import com.fernando.oauth2_custom.infrastructure.adapters.output.restclient.models.responses.CheckCredentialResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="users-service", url="${clients.users-service}")
public interface UserFeignClient {
    @PostMapping("/check")
    CheckCredentialResponse checkCredentials(@RequestBody AuthRequest authRequest);
}
