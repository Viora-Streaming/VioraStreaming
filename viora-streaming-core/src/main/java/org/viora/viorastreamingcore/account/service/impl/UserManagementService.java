package org.viora.viorastreamingcore.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;
import org.viora.viorastreamingcore.account.dto.UpdateAccountRequest;
import org.viora.viorastreamingcore.account.exception.AccountAlreadyExistsException;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.account.service.RegisterUserUseCase;
import org.viora.viorastreamingcore.account.service.UpdateAccountUseCase;
import org.viora.viorastreamingcore.configs.security.SecurityHelpers;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationService;

@Service
@RequiredArgsConstructor
public class UserManagementService implements RegisterUserUseCase, UpdateAccountUseCase {

  private final AccountRepository accountRepository;
  private final VerificationService verificationService;
  private final PasswordEncoder passwordEncoder;
  private final SecurityHelpers securityHelpers;

  @Override
  public Account registerUser(RegisterUserRequest request) {
    if (accountRepository.existsByEmail(request.email())) {
      throw new AccountAlreadyExistsException(
          "Account with email " + request.email() + " already exists");
    }
    AccountModel model = AccountModel.builder()
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .enabled(false)
        .build();

    verificationService.sendVerification(VerificationType.VERIFY_EMAIL, mapToAccountDto(model));
    model = accountRepository.save(model);
    return mapToAccount(model);
  }

  @Override
  public AccountDto updateUser(UpdateAccountRequest request) {
    Long userId = securityHelpers.getCurrentlyAuthenticatedAccountId();
    AccountModel model = accountRepository.findById(userId)
        .orElseThrow(() -> new AccountNotFoundException("Account not found"));

    AccountModel accountModel = AccountModel.builder()
        .id(model.getId())
        .email(model.getEmail())
        .fullName(request.fullName())
        .bio(request.bio())
        .password(model.getPassword())
        .build();

    accountRepository.save(accountModel);
    return mapToAccountDto(accountModel);
  }

  @Override
  public void deleteAccount() {
    Long id = securityHelpers.getCurrentlyAuthenticatedAccountId();
    accountRepository.deleteById(id);
  }

  private AccountDto mapToAccountDto(AccountModel model) {
    return AccountDto.builder()
        .email(model.getEmail())
        .fullName(model.getFullName())
        .bio(model.getBio())
        .build();
  }

  private Account mapToAccount(AccountModel model) {
    return new Account(model.getId(), model.getEmail(), model.getPassword());
  }

}
