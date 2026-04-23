package org.viora.viorastreamingcore.account.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.exception.AccountAlreadyVerifiedException;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.configs.security.JwtTokenService;
import org.viora.viorastreamingcore.mail.messages.VerifyEmailMessage;
import org.viora.viorastreamingcore.mail.services.MailService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationUserServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private JwtTokenService jwtTokenService;

  @Mock
  private MailService mailService;

  private VerificationUserService verificationUserService;

  @BeforeEach
  public void setUp() {
    verificationUserService = new VerificationUserService(accountRepository, jwtTokenService,
        mailService, "localhost", "8080");
  }

  @Test
  void givenTokenIsValidAndUserExistsThenUserShouldBeSaved() {
    // given
    String token = "token";
    when(jwtTokenService.getUsernameFromToken(token)).thenReturn("email");
    when(accountRepository.findByLogin("email")).thenReturn(java.util.Optional.of(
        new AccountModel(1L, "email", "password", false)));

    // when
    verificationUserService.verifyUserAccount(token);
    ArgumentCaptor<AccountModel> captor = ArgumentCaptor.forClass(AccountModel.class);
    verify(accountRepository).save(captor.capture());

    // then
    AccountModel expectedResult = new AccountModel(1L, "email", "password", true);
    assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(expectedResult);
  }

  @Test
  void givenTokenIsValidAndUserNotExistsThenAccountNotFoundExceptionThrown() {
    // given
    String token = "token";
    when(jwtTokenService.getUsernameFromToken(token)).thenReturn("email");
    when(accountRepository.findByLogin("email")).thenReturn(Optional.empty());

    // when & then
    assertThrows(AccountNotFoundException.class,
        () -> verificationUserService.verifyUserAccount(token));
    verify(accountRepository, never()).save(any(AccountModel.class));
  }

  @Test
  void givenTokenIsValidAndUserAlreadyVerifiedThenAccountAccountAlreadyVerifiedExceptionThrown() {
    // given
    String token = "token";
    when(jwtTokenService.getUsernameFromToken(token)).thenReturn("email");
    when(accountRepository.findByLogin("email")).thenReturn(java.util.Optional.of(
        new AccountModel(1L, "email", "password", true)));

    // when
    assertThrows(AccountAlreadyVerifiedException.class,
        () -> verificationUserService.verifyUserAccount(token));
    verify(accountRepository, never()).save(any(AccountModel.class));
  }

  @SneakyThrows
  @Test
  void givenAccountWhenSendNotificationThenValidUrlShouldBeUsed() {
    // given
    Account account = new Account(1L, "email", "password");
    when(jwtTokenService.generateToken(account)).thenReturn("token");

    // when & then
    verificationUserService.sendVerificationForAccount(account);
    ArgumentCaptor<VerifyEmailMessage> captor = ArgumentCaptor.forClass(VerifyEmailMessage.class);
    verify(mailService).sendEmail(eq("email"), captor.capture());
    assertEquals("http://localhost:8080/api/v1/accounts/verify?token=token",
        captor.getValue().getActionUrl());
  }

}