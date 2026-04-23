package org.viora.viorastreamingcore.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;
import org.viora.viorastreamingcore.account.exception.AccountAlreadyExistsException;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class UserManagementService implements RegisterUserUseCase, GetUserAccountUseCase {

  private final AccountRepository accountRepository;
  private final VerifyUserAccountUseCase verifyUserAccountUseCase;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public Account registerUser(RegisterUserRequest request) {
    if (accountRepository.existsByLogin(request.email())) {
      throw new AccountAlreadyExistsException(
          "Account with email " + request.email() + " already exists");
    }
    AccountModel model = new AccountModel(null, request.email(),
        passwordEncoder.encode(request.password()), false);
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

  private Account mapToAccount(AccountModel model) {
    return new Account(model.getId(), model.getLogin(), model.getPassword());
  }

}
