package org.viora.viorastreamingcore.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.account.dto.DropPasswordRequest;
import org.viora.viorastreamingcore.account.service.DropPasswordUseCase;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationService;

@RestController
@RequestMapping("/api/v1/accounts/drop-password")
@RequiredArgsConstructor
public class DropPasswordController {

  public static final String DROP_PASSWORD_TOKEN = "DROP_PASSWORD_TOKEN";

  private final DropPasswordUseCase dropPasswordUseCase;
  private final VerificationService verificationService;

  @GetMapping
  public ResponseEntity<Void> sendDropPasswordRequest(@RequestParam String email) {
    dropPasswordUseCase.dropPassword(email);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> updateUserPassword(
      @Valid @RequestBody DropPasswordRequest request,
      @CookieValue(name = "DROP_PASSWORD_TOKEN", required = false) String token) {

    verificationService.verify(VerificationType.VERIFY_DROP_PASSWORD, token,
        (email) -> dropPasswordUseCase.updatePassword((String) email, request.password()));

    return ResponseEntity.noContent().build();
  }


}
