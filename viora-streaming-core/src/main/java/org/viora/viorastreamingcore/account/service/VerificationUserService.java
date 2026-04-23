package org.viora.viorastreamingcore.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.exception.AccountAlreadyVerifiedException;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.configs.security.JwtTokenService;
import org.viora.viorastreamingcore.mail.messages.VerifyEmailMessage;
import org.viora.viorastreamingcore.mail.services.MailService;

@Service
@RequiredArgsConstructor
public class VerificationUserService implements VerifyUserAccountUseCase {

  private static final String REDIRECT_URL = "http://localhost:8080/api/v1/accounts/verify?token=";

  private final AccountRepository accountRepository;
  private final JwtTokenService jwtTokenService;
  private final MailService mailService;

  @Transactional
  @Override
  public void verifyUserAccount(String token) {
    String login = jwtTokenService.getUsernameFromToken(token);
    AccountModel model = accountRepository.findByLogin(login)
        .orElseThrow(
            () -> new AccountNotFoundException("Account with email " + login + " not found"));
    if (model.isEnabled()) {
      throw new AccountAlreadyVerifiedException(
          "Account with email " + login + " already verified");
    }
    model.setEnabled(true);
    accountRepository.save(model);
  }

  @Override
  public void sendVerificationForAccount(Account account) {
    String token = jwtTokenService.generateToken(account);
    String url = REDIRECT_URL + token;
    try {
      mailService.sendEmail(account.getUsername(), new VerifyEmailMessage(url));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
