package org.viora.viorastreamingcore.account.service;

import jakarta.transaction.Transactional;
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
import org.viora.viorastreamingcore.configs.security.SecurityHelpers;

@Service
@RequiredArgsConstructor
public class UserManagementService implements RegisterUserUseCase, GetUserAccountUseCase,
    UpdateAccountUseCase {

  private final AccountRepository accountRepository;
  private final VerifyUserAccountUseCase verifyUserAccountUseCase;
  private final PasswordEncoder passwordEncoder;
  private final SecurityHelpers securityHelpers;

  @Transactional
  @Override
  public Account registerUser(RegisterUserRequest request) {
    if (accountRepository.existsByLogin(request.email())) {
      throw new AccountAlreadyExistsException(
          "Account with email " + request.email() + " already exists");
    }
    AccountModel model = AccountModel.builder()
        .login(request.email())
        .password(passwordEncoder.encode(request.password()))
        .enabled(false)
        .build();
    verifyUserAccountUseCase.sendVerificationForAccount(mapToAccount(model));
    model = accountRepository.save(model);
    return mapToAccount(model);
  }

  @Override
  public Account findAccountByLogin(String login) {
    return accountRepository.findByLogin(login)
        .map(this::mapToAccount)
        .orElseThrow(
            () -> new AccountNotFoundException("Account with email " + login + " not found"));
  }

  @Override
  public AccountDto getUserAccount() {
    AccountModel model = accountRepository.findById(
            securityHelpers.getCurrentlyAuthenticatedAccountId())
        .orElseThrow(() -> new AccountNotFoundException("Account not verified"));

    return mapToAccountDto(model);
  }

  @Override
  public AccountDto updateUser(UpdateAccountRequest request) {
    Long userId = securityHelpers.getCurrentlyAuthenticatedAccountId();
    AccountModel model = accountRepository.findById(userId)
        .orElseThrow(() -> new AccountNotFoundException("Account not found"));

    AccountModel accountModel = AccountModel.builder()
        .id(model.getId())
        .login(model.getLogin())
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
        .email(model.getLogin())
        .fullName(model.getFullName())
        .bio(model.getBio())
        .build();
  }

  private Account mapToAccount(AccountModel model) {
    return new Account(model.getId(), model.getLogin(), model.getPassword());
  }
}
