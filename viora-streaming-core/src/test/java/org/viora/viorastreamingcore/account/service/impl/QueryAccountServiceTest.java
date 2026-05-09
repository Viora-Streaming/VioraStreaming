package org.viora.viorastreamingcore.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.configs.security.SecurityHelpers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueryAccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private SecurityHelpers securityHelpers;

    @InjectMocks
    private QueryAccountService queryAccountService;


    @Test
    void givenExistingLogin_whenFindAccountByLogin_thenAccountReturned() {
        // given
        AccountModel model = AccountModel.builder()
                .id(1L).email("user@example.com").password("hash").build();
        Account expected = new Account(1L, "user@example.com", "hash");
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));

        // when
        Account result = queryAccountService.findAccountByLogin("user@example.com");

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenUnknownLogin_whenFindAccountByLogin_thenAccountNotFoundExceptionThrown() {
        // given
        when(accountRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        // when & then
        assertThrows(AccountNotFoundException.class,
                () -> queryAccountService.findAccountByLogin("ghost@example.com"));
        verify(objectMapper, never()).convertValue(any(), eq(Account.class));
    }

    @Test
    void givenExistingLogin_whenFindAccountByLogin_thenRepositoryCalledWithExactLogin() {
        // given
        String login = "exact@example.com";
        AccountModel model = AccountModel.builder().id(1L).email(login).build();
        when(accountRepository.findByEmail(login)).thenReturn(Optional.of(model));

        // when
        queryAccountService.findAccountByLogin(login);

        // then
        verify(accountRepository).findByEmail(login);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void givenAuthenticatedUser_whenGetUserAccount_thenAccountDtoReturned() {
        // given
        Long userId = 5L;
        when(securityHelpers.getCurrentlyAuthenticatedAccountId()).thenReturn(userId);
        AccountModel model = AccountModel.builder()
                .id(userId).email("user@example.com").fullName("Full Name").bio("bio").build();
        AccountDto expected = AccountDto.builder()
                .email("user@example.com").fullName("Full Name").bio("bio").build();
        when(accountRepository.findById(userId)).thenReturn(Optional.of(model));
        when(objectMapper.convertValue(model, AccountDto.class)).thenReturn(expected);

        // when
        AccountDto result = queryAccountService.getUserAccount();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenNoAccountForAuthenticatedId_whenGetUserAccount_thenAccountNotFoundExceptionThrown() {
        // given
        Long userId = 99L;
        when(securityHelpers.getCurrentlyAuthenticatedAccountId()).thenReturn(userId);
        when(accountRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(AccountNotFoundException.class,
                () -> queryAccountService.getUserAccount());
        verify(objectMapper, never()).convertValue(any(), eq(AccountDto.class));
    }

    @Test
    void givenAuthenticatedUser_whenGetUserAccount_thenCurrentUserIdUsedForLookup() {
        // given
        Long userId = 12L;
        when(securityHelpers.getCurrentlyAuthenticatedAccountId()).thenReturn(userId);
        AccountModel model = AccountModel.builder().id(userId).email("u@e.com").build();
        when(accountRepository.findById(userId)).thenReturn(Optional.of(model));
        when(objectMapper.convertValue(any(), eq(AccountDto.class))).thenReturn(AccountDto.builder().build());

        // when
        queryAccountService.getUserAccount();

        // then — must look up by the authenticated user's ID, not a hardcoded value
        verify(accountRepository).findById(userId);
        verify(securityHelpers).getCurrentlyAuthenticatedAccountId();
    }
}
