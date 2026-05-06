package org.viora.viorastreamingcore.account.dto;

import lombok.Builder;

@Builder
public record AccountDto(
    String email,
    String fullName,
    String bio
) {

}
