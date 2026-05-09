package org.viora.viorastreamingcore.verification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.verification.service.VerificationTokenIssuer;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Map.Entry;

@Service
@RequiredArgsConstructor
public class JwtVerificationTokenIssuer implements VerificationTokenIssuer {

  private final JwtEncoder encoder;
  private final JwtDecoder decoder;

  @Override
  public String issueToken(AccountDto accountDto) {
    return issueToken(accountDto, Map.of());
  }

  @Override
  public String issueToken(AccountDto accountDto, Map<String, Object> claimsMap) {
    Instant now = Instant.now();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .subject(accountDto.email())
        .claims(cl -> cl.putAll(claimsMap))
        .build();

    var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(),
        claims);

    return this.encoder.encode(encoderParameters).getTokenValue();
  }

  @Override
  public String validateAndGetEmailFromToken(String token, Map<String, Object> claims) {
    Jwt jwt = decoder.decode(token);

    for (Entry<String, Object> claim : claims.entrySet()) {
      String key = jwt.getClaimAsString(claim.getKey());
      if (!key.equals(claim.getValue())) {
        throw new IllegalArgumentException("Invalid token claims");
      }
    }

    return jwt.getSubject();
  }

  @Override
  public String validateAndGetEmailFromToken(String token) {
    return validateAndGetEmailFromToken(token, Map.of());
  }

}
