package org.viora.viorastreamingcore.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.configs.security.JwtTokenService;

@Component
@RequiredArgsConstructor
public class PasswordService implements DropPasswordUseCase {

  private final AccountRepository accountRepository;
  private final VerifyUserAccountUseCase verifyUserAccountUseCase;
  private final JwtTokenService jwtTokenService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void dropPassword(String email) {
    AccountModel model = accountRepository.findByLogin(email)
        .orElseThrow(
            () -> new AccountNotFoundException("Account with email " + email + " not found"));
    Account account = new Account(model.getId(), model.getLogin(), model.getPassword());
    verifyUserAccountUseCase.sendDropPasswordVerification(account);
  }

  @Override
  public void updatePassword(String token, String password) {
    String email = jwtTokenService.getUsernameFromDropPasswordToken(token);
    AccountModel model = accountRepository.findByLogin(email)
        .orElseThrow(
            () -> new AccountNotFoundException("Account with email " + email + " not found"));
    model.setPassword(passwordEncoder.encode(password));
    accountRepository.save(model);
  }
}
