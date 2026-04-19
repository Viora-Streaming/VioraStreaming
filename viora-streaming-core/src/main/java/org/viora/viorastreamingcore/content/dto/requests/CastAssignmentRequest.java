package org.viora.viorastreamingcore.content.dto.requests;

import jakarta.validation.constraints.NotEmpty;

public record CastAssignmentRequest(
    @NotEmpty(message = "Cast member id is required")
    Long castMemberId,

    @NotEmpty(message = "Role name must not be empty")
    String roleName
) {}
