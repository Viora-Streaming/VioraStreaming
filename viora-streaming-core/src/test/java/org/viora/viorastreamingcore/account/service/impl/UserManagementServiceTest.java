package org.viora.viorastreamingcore.account.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;
import org.viora.viorastreamingcore.account.dto.UpdateAccountRequest;
import org.viora.viorastreamingcore.account.exception.AccountAlreadyExistsException;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.configs.security.SecurityHelpers;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private VerificationService verificationService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private SecurityHelpers securityHelpers;

  @InjectMocks
  private UserManagementService userManagementService;

  @Test
  void givenValidRequest_whenRegisterUser_thenAccountSavedAndReturned() {
    // given
    RegisterUserRequest request = new RegisterUserRequest("user@example.com", "secret");
    when(accountRepository.existsByEmail("user@example.com")).thenReturn(false);
    when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");
    AccountModel saved = AccountModel.builder()
        .id(1L).email("user@example.com").password("encoded-secret").enabled(false).build();
    when(accountRepository.save(any(AccountModel.class))).thenReturn(saved);

    // when
    Account result = userManagementService.registerUser(request);

    // then
    assertThat(result.getId()).isEqualTo(1L);
    assertThat(result.getUsername()).isEqualTo("user@example.com");
    verify(passwordEncoder).encode("secret");
    verify(verificationService).sendVerification(eq(VerificationType.VERIFY_EMAIL), any());
  }

  @Test
  void givenValidRequest_whenRegisterUser_thenAccountIsSavedWithEnabledFalse() {
    // given
    RegisterUserRequest request = new RegisterUserRequest("user@example.com", "secret");
    when(accountRepository.existsByEmail(any())).thenReturn(false);
    when(accountRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    // when
    userManagementService.registerUser(request);

    // then — capture and assert the model passed to save
    ArgumentCaptor<AccountModel> captor = ArgumentCaptor.forClass(AccountModel.class);
    verify(accountRepository).save(captor.capture());
    assertThat(captor.getValue().isEnabled()).isFalse();
  }

  @Test
  void givenValidRequest_whenRegisterUser_thenVerificationSentBeforeSave() {
    // given — verification must fire before save so the email goes out even if save fails
    RegisterUserRequest request = new RegisterUserRequest("user@example.com", "secret");
    when(accountRepository.existsByEmail(any())).thenReturn(false);
    when(accountRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    var order = inOrder(verificationService, accountRepository);

    // when
    userManagementService.registerUser(request);

    // then
    order.verify(verificationService).sendVerification(any(), any());
    order.verify(accountRepository).save(any());
  }

  @Test
  void givenExistingLogin_whenRegisterUser_thenAccountAlreadyExistsExceptionThrown() {
    // given
    RegisterUserRequest request = new RegisterUserRequest("user@example.com", "secret");
    when(accountRepository.existsByEmail("user@example.com")).thenReturn(true);

    // when & then
    assertThrows(AccountAlreadyExistsException.class,
        () -> userManagementService.registerUser(request));
    verify(accountRepository, never()).save(any());
    verify(verificationService, never()).sendVerification(any(), any());
  }

  @Test
  void givenValidRequest_whenUpdateUser_thenUpdatedDtoReturned() {
    // given
    Long userId = 42L;
    when(securityHelpers.getCurrentlyAuthenticatedAccountId()).thenReturn(userId);
    AccountModel existing = AccountModel.builder()
        .id(userId).email("user@example.com").password("encoded").build();
    when(accountRepository.findById(userId)).thenReturn(Optional.of(existing));

    UpdateAccountRequest request = new UpdateAccountRequest("Full Name", "My bio");

    // when
    AccountDto result = userManagementService.updateUser(request);

    // then
    assertThat(result.fullName()).isEqualTo("Full Name");
    assertThat(result.bio()).isEqualTo("My bio");
    assertThat(result.email()).isEqualTo("user@example.com");
    verify(accountRepository).save(any(AccountModel.class));
  }

  @Test
  void givenValidRequest_whenUpdateUser_thenLoginAndPasswordArePreserved() {
    // given
    Long userId = 42L;
    when(securityHelpers.getCurrentlyAuthenticatedAccountId()).thenReturn(userId);
    AccountModel existing = AccountModel.builder()
        .id(userId).email("original@example.com").password("original-hash").build();
    when(accountRepository.findById(userId)).thenReturn(Optional.of(existing));

    UpdateAccountRequest request = new UpdateAccountRequest("New Name", "New bio");

    // when
    userManagementService.updateUser(request);

    // then
    ArgumentCaptor<AccountModel> captor = ArgumentCaptor.forClass(AccountModel.class);
    verify(accountRepository).save(captor.capture());
    assertThat(captor.getValue().getEmail()).isEqualTo("original@example.com");
    assertThat(captor.getValue().getPassword()).isEqualTo("original-hash");
  }

  @Test
  void givenUnknownUser_whenUpdateUser_thenAccountNotFoundExceptionThrown() {
    // given
    when(securityHelpers.getCurrentlyAuthenticatedAccountId()).thenReturn(99L);
    when(accountRepository.findById(99L)).thenReturn(Optional.empty());

    // when & then
    assertThrows(AccountNotFoundException.class,
        () -> userManagementService.updateUser(new UpdateAccountRequest("Name", "bio")));
    verify(accountRepository, never()).save(any());
  }

  @Test
  void whenDeleteAccount_thenDeleteByIdCalledWithCurrentUserId() {
    // given
    when(securityHelpers.getCurrentlyAuthenticatedAccountId()).thenReturn(7L);

    // when
    userManagementService.deleteAccount();

    // then
    verify(accountRepository).deleteById(7L);
  }
}
