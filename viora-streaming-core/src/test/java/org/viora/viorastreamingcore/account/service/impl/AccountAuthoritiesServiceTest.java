package org.viora.viorastreamingcore.account.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountAuthoritiesServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountAuthoritiesService accountAuthoritiesService;

  @Test
  void givenExistingEmail_whenEnableAccount_thenAccountSavedWithEnabledTrue() {
    // given
    AccountModel model = AccountModel.builder()
        .id(1L).email("user@example.com").password("hash").enabled(false).build();
    when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));

    // when
    accountAuthoritiesService.enableAccount("user@example.com");

    // then
    ArgumentCaptor<AccountModel> captor = ArgumentCaptor.forClass(AccountModel.class);
    verify(accountRepository).save(captor.capture());
    assertThat(captor.getValue().isEnabled()).isTrue();
  }

  @Test
  void givenAlreadyEnabledAccount_whenEnableAccount_thenStillSavedAsEnabled() {
    // given — idempotency check
    AccountModel model = AccountModel.builder()
        .id(1L).email("user@example.com").password("hash").enabled(true).build();
    when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));

    // when
    accountAuthoritiesService.enableAccount("user@example.com");

    // then
    verify(accountRepository).save(argThat(AccountModel::isEnabled));
  }

  @Test
  void givenUnknownEmail_whenEnableAccount_thenAccountNotFoundExceptionThrown() {
    // given
    when(accountRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

    // when & then
    assertThrows(AccountNotFoundException.class,
        () -> accountAuthoritiesService.enableAccount("ghost@example.com"));
    verify(accountRepository, never()).save(any());
  }

}