package org.viora.viorastreamingcore.account.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;
import org.viora.viorastreamingcore.account.exception.AccountAlreadyExistsException;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private VerifyUserAccountUseCase verifyUserAccountUseCase;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserManagementService userManagementService;


  @Test
  void givenRegisterAccountRequestWhenRegisterThenNewAccountReturned() {
    // given
    RegisterUserRequest request = new RegisterUserRequest("email", "password");
    when(accountRepository.existsByLogin("email")).thenReturn(false);
    when(accountRepository.save(any(AccountModel.class))).thenReturn(
        new AccountModel(1L, "email", "password", false));

    // when
    Account account = userManagementService.registerUser(request);
    verify(passwordEncoder).encode(request.password());
    verify(verifyUserAccountUseCase).sendVerificationForAccount(any(Account.class));

    // then
    Account expectedAccount = new Account(1L, "email", "password");
    assertThat(account).usingRecursiveComparison().isEqualTo(expectedAccount);
  }

  @Test
  void givenRegisterAccountRequestWithExistingLoginWhenRegisterThenAccountAlreadyExistsException() {
    // given
    RegisterUserRequest request = new RegisterUserRequest("email", "password");
    when(accountRepository.existsByLogin("email")).thenReturn(true);

    // when & then
    assertThrows(AccountAlreadyExistsException.class,
        () -> userManagementService.registerUser(request));
    verify(accountRepository, never()).save(any(AccountModel.class));
  }

  @Test
  void givenLoginWhenFindAccountByLoginThenAccountReturned() {
    // given
    String login = "email";
    when(accountRepository.findByLogin(login)).thenReturn(
        Optional.of(new AccountModel(1L, "email", "password", false)));

    // when
    Account account = userManagementService.findAccountByLogin(login);

    // then
    Account expectedAccount = new Account(1L, "email", "password");
    assertThat(account).usingRecursiveComparison().isEqualTo(expectedAccount);
  }

  @Test
  void givenUnknownLoginWhenFindAccountByLoginThenNotFoundReturned() {
    // given
    String login = "email";
    when(accountRepository.findByLogin(login)).thenReturn(Optional.empty());

    // when & then
    assertThrows(AccountNotFoundException.class,
        () -> userManagementService.findAccountByLogin(login));
  }
}