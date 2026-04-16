package org.viora.viorastreamingcore.account.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

  @Mock
  AccountRepository accountRepository;

  @Mock
  VerifyUserAccountUseCase verifyUserAccountUseCase;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserManagementService userManagementService;

  @Test
  void givenRegisterAccountRequestWhenRegisterThenNewAccountReturned() {
    // given
    RegisterUserRequest request = new RegisterUserRequest("login", "password");
    when(accountRepository.existsByLogin("login")).thenReturn(false);
    when(accountRepository.save(any(AccountModel.class))).thenReturn(
        new AccountModel(1L, "login", "password", false));

    // when
    Account account = userManagementService.registerUser(request);
    verify(passwordEncoder).encode(request.password());
    verify(verifyUserAccountUseCase).sendVerificationForAccount(any(Account.class));

    // then
    Account expectedAccount = new Account(1L, "login", "password");
    assertThat(account).usingRecursiveComparison().isEqualTo(expectedAccount);
  }

}