package org.viora.viorastreamingcore.account.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
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
public class VerificationUserService implements VerifyUserAccountUseCase {

  private final String redirectUrl;
  private final String dropPasswordRedirectUrl;
  private final AccountRepository accountRepository;
  private final JwtTokenService jwtTokenService;
  private final MailService mailService;

  public VerificationUserService(AccountRepository accountRepository,
      JwtTokenService jwtTokenService, MailService mailService,
      @Value("${server.host}") String host, @Value("${server.port}") String port) {
    this.accountRepository = accountRepository;
    this.jwtTokenService = jwtTokenService;
    this.mailService = mailService;
    this.redirectUrl = String.format("http://%s:%s/api/v1/accounts/verify?token=", host, port);
    this.dropPasswordRedirectUrl = String.format(
        "http://%s:%s/api/v1/accounts/drop-password/verify?token=", host, port);
  }

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
    String url = redirectUrl + token;
    try {
      mailService.sendEmail(account.getUsername(), new VerifyEmailMessage(url));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void sendDropPasswordVerification(Account account) {
    String token = jwtTokenService.generateDropPasswordToken(account); // use dedicated token
    String url = dropPasswordRedirectUrl + token;
    try {
      mailService.sendEmail(account.getUsername(), new VerifyEmailMessage(url));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void verifyDropPassword(String token) {
    jwtTokenService.getUsernameFromToken(token);
  }
}
