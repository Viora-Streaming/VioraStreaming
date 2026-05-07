package org.viora.viorastreamingcore.account.controller;

import io.swagger.v3.oas.annotations.headers.Header;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.dto.DropPasswordRequest;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;
import org.viora.viorastreamingcore.account.dto.UpdateAccountRequest;
import org.viora.viorastreamingcore.account.service.DropPasswordUseCase;
import org.viora.viorastreamingcore.account.service.GetUserAccountUseCase;
import org.viora.viorastreamingcore.account.service.RegisterUserUseCase;
import org.viora.viorastreamingcore.account.service.UpdateAccountUseCase;
import org.viora.viorastreamingcore.account.service.UserManagementService;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountsController {

  private final RegisterUserUseCase registerUserUseCase;
  private final UpdateAccountUseCase updateAccountUseCase;
  private final GetUserAccountUseCase getUserAccountUseCase;
  private final DropPasswordUseCase dropPasswordUseCase;

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterUserRequest request) {
    registerUserUseCase.registerUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<AccountDto> getUserAccount() {
    return ResponseEntity.ok(getUserAccountUseCase.getUserAccount());
  }

  @PatchMapping
  public ResponseEntity<AccountDto> updateUserAccount(@RequestBody UpdateAccountRequest request) {
    AccountDto account = updateAccountUseCase.updateUser(request);
    return ResponseEntity.ok(account);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteUserAccount() {
    updateAccountUseCase.deleteAccount();
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/drop-password")
  public ResponseEntity<Void> sendDropPasswordRequest(@RequestParam String email) {
    dropPasswordUseCase.dropPassword(email);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/drop-password")
  public ResponseEntity<Void> updateUserPassword(
      @Valid @RequestBody DropPasswordRequest request,
      @CookieValue(name = "DROP_PASSWORD_TOKEN", required = false) String token) {

    if (token == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    dropPasswordUseCase.updatePassword(token, request.password());

    return ResponseEntity.noContent().build();
  }

}
