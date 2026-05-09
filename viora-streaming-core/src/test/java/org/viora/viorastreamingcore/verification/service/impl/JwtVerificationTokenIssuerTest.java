package org.viora.viorastreamingcore.verification.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.viora.viorastreamingcore.account.dto.AccountDto;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtVerificationTokenIssuerTest {

  @Mock
  private JwtEncoder encoder;

  @Mock
  private JwtDecoder decoder;

  @InjectMocks
  private JwtVerificationTokenIssuer issuer;

  private final AccountDto accountDto = AccountDto.builder()
      .email("user@example.com")
      .build();

  // ─── issueToken (no claims) ───────────────────────────────────────────────

  @Test
  void givenAccount_whenIssueToken_thenTokenValueReturned() {
    Jwt jwt = buildJwt("user@example.com", Map.of());
    when(encoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

    String result = issuer.issueToken(accountDto);

    assertThat(result).isEqualTo("mock-token-value");
  }

  @Test
  void givenAccount_whenIssueToken_thenEncoderInvokedOnce() {
    Jwt jwt = buildJwt("user@example.com", Map.of());
    when(encoder.encode(any())).thenReturn(jwt);

    issuer.issueToken(accountDto);

    verify(encoder, times(1)).encode(any());
  }

  // ─── issueToken (with claims) ─────────────────────────────────────────────

  @Test
  void givenAccountAndClaims_whenIssueToken_thenTokenValueReturned() {
    Jwt jwt = buildJwt("user@example.com", Map.of("verify_email", "true"));
    when(encoder.encode(any())).thenReturn(jwt);

    String result = issuer.issueToken(accountDto, Map.of("verify_email", "true"));

    assertThat(result).isEqualTo("mock-token-value");
  }

  @Test
  void givenAccountAndEmptyClaims_whenIssueToken_thenDelegatesCorrectly() {
    // issueToken(dto) must delegate to issueToken(dto, Map.of())
    Jwt jwt = buildJwt("user@example.com", Map.of());
    when(encoder.encode(any())).thenReturn(jwt);

    // call the no-claims overload
    issuer.issueToken(accountDto);

    ArgumentCaptor<JwtEncoderParameters> captor =
        ArgumentCaptor.forClass(JwtEncoderParameters.class);
    verify(encoder).encode(captor.capture());
    // subject must be the account email
    assertThat(captor.getValue().getClaims().getSubject()).isEqualTo("user@example.com");
  }

  @Test
  void givenAccountAndClaims_whenIssueToken_thenClaimsIncludedInJwtParameters() {
    Jwt jwt = buildJwt("user@example.com", Map.of("drop_password", "true"));
    when(encoder.encode(any())).thenReturn(jwt);

    issuer.issueToken(accountDto, Map.of("drop_password", "true"));

    ArgumentCaptor<JwtEncoderParameters> captor =
        ArgumentCaptor.forClass(JwtEncoderParameters.class);
    verify(encoder).encode(captor.capture());
    String res = (String) Optional.of(captor.getValue().getClaims().getClaim("drop_password"))
        .orElse("false");
    assertThat(res).isEqualTo(
        "true");
  }

  @Test
  void givenAccount_whenIssueToken_thenSubjectIsAccountEmail() {
    Jwt jwt = buildJwt("user@example.com", Map.of());
    when(encoder.encode(any())).thenReturn(jwt);

    issuer.issueToken(accountDto);

    ArgumentCaptor<JwtEncoderParameters> captor =
        ArgumentCaptor.forClass(JwtEncoderParameters.class);
    verify(encoder).encode(captor.capture());
    assertThat(captor.getValue().getClaims().getSubject()).isEqualTo("user@example.com");
  }

  @Test
  void givenAccount_whenIssueToken_thenExpiresInOneHour() {
    Jwt jwt = buildJwt("user@example.com", Map.of());
    when(encoder.encode(any())).thenReturn(jwt);

    Instant before = Instant.now();
    issuer.issueToken(accountDto);
    Instant after = Instant.now();

    ArgumentCaptor<JwtEncoderParameters> captor =
        ArgumentCaptor.forClass(JwtEncoderParameters.class);
    verify(encoder).encode(captor.capture());
    Instant expiresAt = captor.getValue().getClaims().getExpiresAt();
    assertThat(expiresAt).isAfter(before.plusSeconds(3599));
    assertThat(expiresAt).isBefore(after.plusSeconds(3601));
  }

  // ─── validateAndGetEmailFromToken (no claims) ─────────────────────────────

  @Test
  void givenValidToken_whenValidateWithNoClaims_thenSubjectReturned() {
    Jwt jwt = buildJwt("user@example.com", Map.of());
    when(decoder.decode("valid-token")).thenReturn(jwt);

    String email = issuer.validateAndGetEmailFromToken("valid-token");

    assertThat(email).isEqualTo("user@example.com");
  }

  // ─── validateAndGetEmailFromToken (with claims) ───────────────────────────

  @Test
  void givenValidTokenWithMatchingClaims_whenValidate_thenSubjectReturned() {
    Jwt jwt = buildJwt("user@example.com", Map.of("verify_email", "true"));
    when(decoder.decode("valid-token")).thenReturn(jwt);

    String email = issuer.validateAndGetEmailFromToken(
        "valid-token", Map.of("verify_email", "true"));

    assertThat(email).isEqualTo("user@example.com");
  }

  @Test
  void givenTokenWithMissingClaim_whenValidate_thenNullPointerExceptionThrown() {
    // jwt.getClaimAsString() returns null → null.equals(...) → NPE
    // this documents the current gap; callers should ensure tokens have the right claims
    Jwt jwt = buildJwt("user@example.com", Map.of());   // no "verify_email" claim
    when(decoder.decode("token-missing-claim")).thenReturn(jwt);

    assertThrows(NullPointerException.class,
        () -> issuer.validateAndGetEmailFromToken(
            "token-missing-claim", Map.of("verify_email", "true")));
  }

  @Test
  void givenTokenWithWrongClaimValue_whenValidate_thenIllegalArgumentExceptionThrown() {
    Jwt jwt = buildJwt("user@example.com", Map.of("verify_email", "wrong-value"));
    when(decoder.decode("bad-claims-token")).thenReturn(jwt);

    assertThrows(IllegalArgumentException.class,
        () -> issuer.validateAndGetEmailFromToken(
            "bad-claims-token", Map.of("verify_email", "true")));
  }

  @Test
  void givenTokenWithMultipleClaimsAndOneWrong_whenValidate_thenIllegalArgumentExceptionThrown() {
    Jwt jwt = buildJwt("user@example.com",
        Map.of("verify_email", "true", "drop_password", "wrong"));
    when(decoder.decode("partial-claims-token")).thenReturn(jwt);

    assertThrows(IllegalArgumentException.class,
        () -> issuer.validateAndGetEmailFromToken(
            "partial-claims-token",
            Map.of("verify_email", "true", "drop_password", "true")));
  }

  @Test
  void givenTokenWithAllCorrectClaims_whenValidate_thenEmailReturned() {
    Jwt jwt = buildJwt("user@example.com",
        Map.of("verify_email", "true", "custom", "value"));
    when(decoder.decode("multi-claim-token")).thenReturn(jwt);

    String email = issuer.validateAndGetEmailFromToken(
        "multi-claim-token",
        Map.of("verify_email", "true", "custom", "value"));

    assertThat(email).isEqualTo("user@example.com");
  }

  @Test
  void givenEmptyRequiredClaims_whenValidate_thenSubjectReturnedWithoutClaimChecks() {
    Jwt jwt = buildJwt("user@example.com", Map.of());
    when(decoder.decode("token")).thenReturn(jwt);

    String email = issuer.validateAndGetEmailFromToken("token", Map.of());

    assertThat(email).isEqualTo("user@example.com");
    verify(decoder).decode("token");
  }

  // ─── helpers ─────────────────────────────────────────────────────────────────

  private Jwt buildJwt(String subject, Map<String, Object> extraClaims) {
    Instant now = Instant.now();
    Map<String, Object> headers = Map.of("alg", "HS256");
    Map<String, Object> claims = new java.util.HashMap<>();
    claims.put("sub", subject);
    claims.put("iss", "self");
    claims.put("iat", now);
    claims.put("exp", now.plusSeconds(3600));
    claims.putAll(extraClaims);
    return new Jwt("mock-token-value", now, now.plusSeconds(3600), headers, claims);
  }
}
