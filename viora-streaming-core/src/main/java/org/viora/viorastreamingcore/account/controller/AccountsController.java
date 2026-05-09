package org.viora.viorastreamingcore.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;
import org.viora.viorastreamingcore.account.dto.UpdateAccountRequest;
import org.viora.viorastreamingcore.account.service.GetUserAccountUseCase;
import org.viora.viorastreamingcore.account.service.RegisterUserUseCase;
import org.viora.viorastreamingcore.account.service.UpdateAccountUseCase;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountsController {

  private final RegisterUserUseCase registerUserUseCase;
  private final UpdateAccountUseCase updateAccountUseCase;
  private final GetUserAccountUseCase getUserAccountUseCase;

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

}
