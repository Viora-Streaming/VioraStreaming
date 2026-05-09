package org.viora.viorastreamingcore.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DropAccountsPasswordServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationService verificationService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DropAccountsPasswordService dropAccountsPasswordService;

    @Test
    void givenExistingEmail_whenDropPassword_thenVerificationSentWithCorrectType() {
        // given
        AccountModel model = AccountModel.builder()
                .id(1L).email("user@example.com").password("hash").build();
        AccountDto dto = AccountDto.builder().email("user@example.com").build();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));
        when(objectMapper.convertValue(model, AccountDto.class)).thenReturn(dto);

        // when
        dropAccountsPasswordService.dropPassword("user@example.com");

        // then
        verify(verificationService).sendVerification(
                eq(VerificationType.VERIFY_DROP_PASSWORD), eq(dto));
    }

    @Test
    void givenExistingEmail_whenDropPassword_thenAccountDtoMappedFromModel() {
        // given
        AccountModel model = AccountModel.builder()
                .id(1L).email("user@example.com").fullName("User Name").build();
        AccountDto dto = AccountDto.builder().email("user@example.com").fullName("User Name").build();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));
        when(objectMapper.convertValue(model, AccountDto.class)).thenReturn(dto);

        // when
        dropAccountsPasswordService.dropPassword("user@example.com");

        // then
        ArgumentCaptor<AccountDto> captor = ArgumentCaptor.forClass(AccountDto.class);
        verify(verificationService).sendVerification(any(), captor.capture());
        assertThat(captor.getValue().email()).isEqualTo("user@example.com");
        assertThat(captor.getValue().fullName()).isEqualTo("User Name");
    }

    @Test
    void givenUnknownEmail_whenDropPassword_thenAccountNotFoundExceptionThrown() {
        // given
        when(accountRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        // when & then
        assertThrows(AccountNotFoundException.class,
                () -> dropAccountsPasswordService.dropPassword("ghost@example.com"));
        verify(verificationService, never()).sendVerification(any(), any());
    }

    @Test
    void givenExistingEmail_whenDropPassword_thenPasswordIsNotChanged() {
        // given
        AccountModel model = AccountModel.builder()
                .id(1L).email("user@example.com").password("original-hash").build();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));
        when(objectMapper.convertValue(any(), eq(AccountDto.class))).thenReturn(AccountDto.builder().build());

        // when
        dropAccountsPasswordService.dropPassword("user@example.com");

        // then — drop password should NOT touch the repository for saving
        verify(accountRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void givenExistingEmail_whenUpdatePassword_thenPasswordEncodedAndSaved() {
        // given
        AccountModel model = AccountModel.builder()
                .id(1L).email("user@example.com").password("old-hash").build();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));
        when(passwordEncoder.encode("new-password")).thenReturn("new-hash");

        // when
        dropAccountsPasswordService.updatePassword("user@example.com", "new-password");

        // then
        ArgumentCaptor<AccountModel> captor = ArgumentCaptor.forClass(AccountModel.class);
        verify(accountRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("new-hash");
    }

    @Test
    void givenExistingEmail_whenUpdatePassword_thenRawPasswordIsNeverStored() {
        // given
        AccountModel model = AccountModel.builder()
                .id(1L).email("user@example.com").password("old-hash").build();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));
        when(passwordEncoder.encode("plain-text")).thenReturn("encoded");

        // when
        dropAccountsPasswordService.updatePassword("user@example.com", "plain-text");

        // then
        ArgumentCaptor<AccountModel> captor = ArgumentCaptor.forClass(AccountModel.class);
        verify(accountRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isNotEqualTo("plain-text");
    }

    @Test
    void givenExistingEmail_whenUpdatePassword_thenOtherFieldsUnchanged() {
        // given
        AccountModel model = AccountModel.builder()
                .id(1L).email("user@example.com").fullName("Full Name").password("old-hash").enabled(true).build();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));
        when(passwordEncoder.encode(any())).thenReturn("new-hash");

        // when
        dropAccountsPasswordService.updatePassword("user@example.com", "new-password");

        // then
        ArgumentCaptor<AccountModel> captor = ArgumentCaptor.forClass(AccountModel.class);
        verify(accountRepository).save(captor.capture());
        // setPassword mutates the existing model, so other fields should be untouched
        assertThat(captor.getValue().getEmail()).isEqualTo("user@example.com");
        assertThat(captor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    void givenUnknownEmail_whenUpdatePassword_thenAccountNotFoundExceptionThrown() {
        // given
        when(accountRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        // when & then
        assertThrows(AccountNotFoundException.class,
                () -> dropAccountsPasswordService.updatePassword("ghost@example.com", "new-pass"));
        verify(accountRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void givenExistingEmail_whenUpdatePassword_thenVerificationNotSent() {
        // given — updating password does not require re-verification
        AccountModel model = AccountModel.builder()
                .id(1L).email("user@example.com").password("old-hash").build();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(model));
        when(passwordEncoder.encode(any())).thenReturn("new-hash");

        // when
        dropAccountsPasswordService.updatePassword("user@example.com", "new-password");

        // then
        verifyNoInteractions(verificationService);
    }
}
