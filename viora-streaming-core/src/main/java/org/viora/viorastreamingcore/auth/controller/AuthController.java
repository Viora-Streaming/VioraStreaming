package org.viora.viorastreamingcore.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.auth.dto.LoginUserRequest;
import org.viora.viorastreamingcore.auth.service.LoginUserUseCase;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final LoginUserUseCase loginUserUseCase;

  @PostMapping
  public ResponseEntity<String> authenticate(@Valid @RequestBody LoginUserRequest request) {
    return ResponseEntity.ok(loginUserUseCase.loginUser(request));
  }


}
