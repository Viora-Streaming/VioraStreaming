package org.viora.viorastreamingcore.verification.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.service.DropPasswordUseCase;
import org.viora.viorastreamingcore.account.service.EnableAccountUseCase;
import org.viora.viorastreamingcore.mail.messages.EmailMessage;
import org.viora.viorastreamingcore.mail.messages.VerifyEmailMessage;
import org.viora.viorastreamingcore.mail.services.MailService;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationTokenIssuer;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationStrategyTest {

    @Mock
    private MailService mailService;

    @Mock
    private VerificationTokenIssuer tokenIssuer;

    @Mock
    private EnableAccountUseCase enableAccountUseCase;

    private final AccountDto accountDto = AccountDto.builder()
            .email("user@example.com")
            .build();

    // ═══════════════════════════════════════════════════════════════════════════
    // EmailVerificationStrategy
    // ═══════════════════════════════════════════════════════════════════════════

    @Nested
    class EmailVerificationStrategyTest {

        private static final String CALLBACK_URL = "http://localhost:8080/verify?token=";

        private EmailVerificationStrategy strategy;

        @BeforeEach
        void setUp() {
            strategy = new EmailVerificationStrategy(
                    mailService, CALLBACK_URL, enableAccountUseCase, tokenIssuer);
        }

        // ─── canVerify ────────────────────────────────────────────────────────

        @Test
        void whenCanVerifyWithVerifyEmailType_thenReturnsTrue() {
            assertTrue(strategy.canVerify(VerificationType.VERIFY_EMAIL));
        }

        @Test
        void whenCanVerifyWithDropPasswordType_thenReturnsFalse() {
            assertFalse(strategy.canVerify(VerificationType.VERIFY_DROP_PASSWORD));
        }

        // ─── sendVerification ─────────────────────────────────────────────────

        @Test
        void givenAccount_whenSendVerification_thenEmailSentToCorrectAddress() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("jwt-token");

            strategy.sendVerification(accountDto);

            verify(mailService).sendEmail(eq("user@example.com"), any(EmailMessage.class));
        }

        @Test
        void givenAccount_whenSendVerification_thenTokenIssuedWithEmailClaim() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("jwt-token");

            strategy.sendVerification(accountDto);

            ArgumentCaptor<Map<String, Object>> claimsCaptor = ArgumentCaptor.forClass(Map.class);
            verify(tokenIssuer).issueToken(eq(accountDto), claimsCaptor.capture());
            assertThat(claimsCaptor.getValue()).isEqualTo(Map.of("verify_email", "true"));
        }

        @Test
        void givenAccount_whenSendVerification_thenCallbackUrlContainsIssuedToken() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("abc123");

            strategy.sendVerification(accountDto);

            ArgumentCaptor<EmailMessage> messageCaptor = ArgumentCaptor.forClass(EmailMessage.class);
            verify(mailService).sendEmail(any(), messageCaptor.capture());
            VerifyEmailMessage sent = (VerifyEmailMessage) messageCaptor.getValue();
            assertThat(sent.getActionUrl()).isEqualTo(CALLBACK_URL + "abc123");
        }

        @Test
        void givenMailServiceThrows_whenSendVerification_thenRuntimeExceptionWrapped() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("token");
            doThrow(new RuntimeException("SMTP down")).when(mailService).sendEmail(any(), any());

            assertThrows(RuntimeException.class, () -> strategy.sendVerification(accountDto));
        }

        @Test
        void givenTokenIssuerThrows_whenSendVerification_thenRuntimeExceptionWrapped() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap()))
                    .thenThrow(new RuntimeException("signing failure"));

            assertThrows(RuntimeException.class, () -> strategy.sendVerification(accountDto));
            verifyNoInteractions(mailService);
        }

        // ─── verify ──────────────────────────────────────────────────────────

        @Test
        void givenValidToken_whenVerify_thenEmailExtractedAndAccountEnabled() {
            when(tokenIssuer.validateAndGetEmailFromToken("valid-token", Map.of("verify_email", "true")))
                    .thenReturn("user@example.com");

            strategy.verify("valid-token");

            verify(enableAccountUseCase).enableAccount("user@example.com");
        }

        @Test
        void givenValidToken_whenVerify_thenTokenValidatedWithEmailClaim() {
            when(tokenIssuer.validateAndGetEmailFromToken(eq("valid-token"), anyMap()))
                    .thenReturn("user@example.com");

            strategy.verify("valid-token");

            ArgumentCaptor<Map<String, Object>> claimsCaptor = ArgumentCaptor.forClass(Map.class);
            verify(tokenIssuer).validateAndGetEmailFromToken(eq("valid-token"), claimsCaptor.capture());
            assertThat(claimsCaptor.getValue()).isEqualTo(Map.of("verify_email", "true"));
        }

        @Test
        void givenInvalidToken_whenVerify_thenIllegalArgumentExceptionPropagated() {
            when(tokenIssuer.validateAndGetEmailFromToken(eq("bad-token"), anyMap()))
                    .thenThrow(new IllegalArgumentException("Invalid token claims"));

            assertThrows(IllegalArgumentException.class, () -> strategy.verify("bad-token"));
            verifyNoInteractions(enableAccountUseCase);
        }

        @Test
        void givenTokenWithWrongClaims_whenVerify_thenEnableAccountNeverCalled() {
            when(tokenIssuer.validateAndGetEmailFromToken(eq("wrong-claims-token"), anyMap()))
                    .thenThrow(new IllegalArgumentException("Invalid token claims"));

            assertThrows(IllegalArgumentException.class,
                    () -> strategy.verify("wrong-claims-token"));
            verifyNoInteractions(enableAccountUseCase);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // DropPasswordVerificationStrategy
    // ═══════════════════════════════════════════════════════════════════════════

    @Nested
    class DropPasswordVerificationStrategyTest {

        private static final String DROP_CALLBACK_URL = "http://localhost:8080/drop-password?token=";

        private DropPasswordVerificationStrategy strategy;

        @BeforeEach
        void setUp() {
            strategy = new DropPasswordVerificationStrategy(
                    mailService, DROP_CALLBACK_URL, tokenIssuer);
        }

        // ─── canVerify ────────────────────────────────────────────────────────

        @Test
        void whenCanVerifyWithDropPasswordType_thenReturnsTrue() {
            assertTrue(strategy.canVerify(VerificationType.VERIFY_DROP_PASSWORD));
        }

        @Test
        void whenCanVerifyWithVerifyEmailType_thenReturnsFalse() {
            assertFalse(strategy.canVerify(VerificationType.VERIFY_EMAIL));
        }

        // ─── sendVerification ─────────────────────────────────────────────────

        @Test
        void givenAccount_whenSendVerification_thenEmailSentToCorrectAddress() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("drop-token");

            strategy.sendVerification(accountDto);

            verify(mailService).sendEmail(eq("user@example.com"), any(EmailMessage.class));
        }

        @Test
        void givenAccount_whenSendVerification_thenTokenIssuedWithDropPasswordClaim() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("drop-token");

            strategy.sendVerification(accountDto);

            ArgumentCaptor<Map<String, Object>> claimsCaptor = ArgumentCaptor.forClass(Map.class);
            verify(tokenIssuer).issueToken(eq(accountDto), claimsCaptor.capture());
            assertThat(claimsCaptor.getValue()).isEqualTo(Map.of("drop_password", "true"));
        }

        @Test
        void givenAccount_whenSendVerification_thenCallbackUrlContainsIssuedToken() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("xyz789");

            strategy.sendVerification(accountDto);

            ArgumentCaptor<EmailMessage> messageCaptor = ArgumentCaptor.forClass(EmailMessage.class);
            verify(mailService).sendEmail(any(), messageCaptor.capture());
            VerifyEmailMessage sent = (VerifyEmailMessage) messageCaptor.getValue();
            assertThat(sent.getActionUrl()).isEqualTo(DROP_CALLBACK_URL + "xyz789");
        }

        @Test
        void givenAccount_whenSendVerification_thenMessageContainsConfirmDropPasswordAction() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("token");

            strategy.sendVerification(accountDto);

            ArgumentCaptor<EmailMessage> messageCaptor = ArgumentCaptor.forClass(EmailMessage.class);
            verify(mailService).sendEmail(any(), messageCaptor.capture());
            VerifyEmailMessage sent = (VerifyEmailMessage) messageCaptor.getValue();
            assertThat(sent.getActionText()).isEqualTo("Confirm Drop Password");
        }

        @Test
        void givenMailServiceThrows_whenSendVerification_thenRuntimeExceptionWrapped() throws Exception {
            when(tokenIssuer.issueToken(eq(accountDto), anyMap())).thenReturn("token");
            doThrow(new RuntimeException("SMTP down")).when(mailService).sendEmail(any(), any());

            assertThrows(RuntimeException.class, () -> strategy.sendVerification(accountDto));
        }

        // ─── verify ──────────────────────────────────────────────────────────

        @Test
        void givenValidToken_whenVerify_thenTokenValidatedWithDropPasswordClaim() {
            strategy.verify("valid-token");

            ArgumentCaptor<Map<String, Object>> claimsCaptor = ArgumentCaptor.forClass(Map.class);
            verify(tokenIssuer).validateAndGetEmailFromToken(eq("valid-token"), claimsCaptor.capture());
            assertThat(claimsCaptor.getValue()).isEqualTo(Map.of("drop_password", "true"));
        }

        @Test
        void givenValidToken_whenVerify_thenNoSideEffectsBeyondTokenValidation() {
            // drop-password verify only validates the token; account state change
            // happens later via a separate updatePassword call
            strategy.verify("valid-token");

            verify(tokenIssuer).validateAndGetEmailFromToken(eq("valid-token"), anyMap());
            verifyNoMoreInteractions(tokenIssuer);
            verifyNoInteractions(mailService);
        }

        @Test
        void givenInvalidToken_whenVerify_thenIllegalArgumentExceptionPropagated() {
            when(tokenIssuer.validateAndGetEmailFromToken(eq("bad-token"), anyMap()))
                    .thenThrow(new IllegalArgumentException("Invalid token claims"));

            assertThrows(IllegalArgumentException.class, () -> strategy.verify("bad-token"));
        }

        @Test
        void givenTokenWithWrongClaims_whenVerify_thenExceptionPropagated() {
            // a token valid for email-verify must be rejected by drop-password strategy
            when(tokenIssuer.validateAndGetEmailFromToken(eq("email-verify-token"), anyMap()))
                    .thenThrow(new IllegalArgumentException("Invalid token claims"));

            assertThrows(IllegalArgumentException.class,
                    () -> strategy.verify("email-verify-token"));
        }
    }
}
