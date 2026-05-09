package org.viora.viorastreamingcore.verification.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationStrategy;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StrategyVerificationServiceTest {

    @Mock
    private VerificationStrategy emailStrategy;

    @Mock
    private VerificationStrategy dropPasswordStrategy;

    private StrategyVerificationService service;

    private final AccountDto accountDto = AccountDto.builder().email("user@example.com").build();

    @BeforeEach
    void setUp() {
        service = new StrategyVerificationService(Map.of(
                VerificationType.VERIFY_EMAIL, List.of(emailStrategy),
                VerificationType.VERIFY_DROP_PASSWORD, List.of(dropPasswordStrategy)
        ));
    }

    // ─── sendVerification ────────────────────────────────────────────────────────

    @Test
    void givenValidTypeAndDto_whenSendVerification_thenMatchingStrategyInvoked() {
        service.sendVerification(VerificationType.VERIFY_EMAIL, accountDto);

        verify(emailStrategy).sendVerification(accountDto);
        verifyNoInteractions(dropPasswordStrategy);
    }

    @Test
    void givenDropPasswordType_whenSendVerification_thenOnlyDropPasswordStrategyInvoked() {
        service.sendVerification(VerificationType.VERIFY_DROP_PASSWORD, accountDto);

        verify(dropPasswordStrategy).sendVerification(accountDto);
        verifyNoInteractions(emailStrategy);
    }

    @Test
    void givenMultipleStrategiesForType_whenSendVerification_thenAllInvoked() {
        VerificationStrategy second = mock(VerificationStrategy.class);
        StrategyVerificationService multiService = new StrategyVerificationService(Map.of(
                VerificationType.VERIFY_EMAIL, List.of(emailStrategy, second)
        ));

        multiService.sendVerification(VerificationType.VERIFY_EMAIL, accountDto);

        verify(emailStrategy).sendVerification(accountDto);
        verify(second).sendVerification(accountDto);
    }

    @Test
    void givenNullType_whenSendVerification_thenIllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> service.sendVerification(null, accountDto));
        verifyNoInteractions(emailStrategy, dropPasswordStrategy);
    }

    @Test
    void givenNullAccountDto_whenSendVerification_thenIllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> service.sendVerification(VerificationType.VERIFY_EMAIL, null));
        verifyNoInteractions(emailStrategy, dropPasswordStrategy);
    }

    @Test
    void givenTypeWithNoRegisteredStrategies_whenSendVerification_thenNullPointerExceptionThrown() {
        // strategies map has no entry for this type — get() returns null, forEach() will NPE
        // this documents current behaviour; callers must ensure the type is registered
        StrategyVerificationService emptyService =
                new StrategyVerificationService(Map.of());

        assertThrows(NullPointerException.class,
                () -> emptyService.sendVerification(VerificationType.VERIFY_EMAIL, accountDto));
    }

    @Test
    void givenStrategyThrows_whenSendVerification_thenExceptionPropagated() {
        doThrow(new RuntimeException("mail failure"))
                .when(emailStrategy).sendVerification(accountDto);

        assertThrows(RuntimeException.class,
                () -> service.sendVerification(VerificationType.VERIFY_EMAIL, accountDto));
    }

    // ─── verify ──────────────────────────────────────────────────────────────────

    @Test
    void givenValidTypeAndToken_whenVerify_thenMatchingStrategyInvoked() {
        service.verify(VerificationType.VERIFY_EMAIL, "some-token");

        verify(emailStrategy).verify("some-token");
        verifyNoInteractions(dropPasswordStrategy);
    }

    @Test
    void givenDropPasswordType_whenVerify_thenOnlyDropPasswordStrategyInvoked() {
        service.verify(VerificationType.VERIFY_DROP_PASSWORD, "some-token");

        verify(dropPasswordStrategy).verify("some-token");
        verifyNoInteractions(emailStrategy);
    }

    @Test
    void givenMultipleStrategiesForType_whenVerify_thenAllInvoked() {
        VerificationStrategy second = mock(VerificationStrategy.class);
        StrategyVerificationService multiService = new StrategyVerificationService(Map.of(
                VerificationType.VERIFY_EMAIL, List.of(emailStrategy, second)
        ));

        multiService.verify(VerificationType.VERIFY_EMAIL, "token");

        verify(emailStrategy).verify("token");
        verify(second).verify("token");
    }

    @Test
    void givenNullType_whenVerify_thenIllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> service.verify(null, "token"));
        verifyNoInteractions(emailStrategy, dropPasswordStrategy);
    }

    @Test
    void givenNullToken_whenVerify_thenIllegalArgumentExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> service.verify(VerificationType.VERIFY_EMAIL, null));
        verifyNoInteractions(emailStrategy, dropPasswordStrategy);
    }

    @Test
    void givenTypeWithNoRegisteredStrategies_whenVerify_thenNullPointerExceptionThrown() {
        StrategyVerificationService emptyService =
                new StrategyVerificationService(Map.of());

        assertThrows(NullPointerException.class,
                () -> emptyService.verify(VerificationType.VERIFY_EMAIL, "token"));
    }

    @Test
    void givenStrategyThrows_whenVerify_thenExceptionPropagated() {
        doThrow(new IllegalArgumentException("bad token"))
                .when(emailStrategy).verify("bad-token");

        assertThrows(IllegalArgumentException.class,
                () -> service.verify(VerificationType.VERIFY_EMAIL, "bad-token"));
    }
}
