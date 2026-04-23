package org.viora.viorastreamingcore.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(
    @NotEmpty(message = "Login must not be empty")
    @Email(message = "Login must be a valid email")
    String login,

    @NotEmpty(message = "Password must not be empty")
    String password
) {

}
