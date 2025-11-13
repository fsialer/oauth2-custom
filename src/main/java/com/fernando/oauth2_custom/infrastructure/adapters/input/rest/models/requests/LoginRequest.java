package com.fernando.oauth2_custom.infrastructure.adapters.input.rest.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Field email cannot be blank")
    @Email(message = "Field email must be a valid email")
    private String email;
    @NotBlank(message = "Field password cannot be blank")
    @Length(min = 5,message = "Field password least 5 characters.")
    private String password;
}