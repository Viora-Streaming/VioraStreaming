package org.viora.viorastreamingcore.account.dto;

import jakarta.validation.constraints.NotEmpty;

public record DropPasswordRequest(
    @NotEmpty
    String password
) {

}
