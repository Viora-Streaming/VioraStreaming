package org.viora.viorastreamingcore.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be a valid email")
    String email,

    @NotEmpty(message = "Password must not be empty")
    String password
) {

}
