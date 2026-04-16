package org.viora.viorastreamingcore.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;
import org.viora.viorastreamingcore.account.service.UserManagementService;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountsController {

  private final UserManagementService userManagementService;

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterUserRequest request) {
    userManagementService.registerUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
