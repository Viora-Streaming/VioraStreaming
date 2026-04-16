package org.viora.viorastreamingcore.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(
    @NotEmpty(message = "Login must not be empty")
    @Email(message = "Login must be a valid email")
    String login,

    @NotEmpty(message = "Password must not be empty")
    String password
) {

}
