package org.viora.viorastreamingcore.verification.service;

import org.viora.viorastreamingcore.account.dto.AccountDto;
import java.util.Map;

public interface VerificationTokenIssuer {

  String issueToken(AccountDto accountDto);

  String issueToken(AccountDto accountDto, Map<String, Object> claims);

  String validateAndGetEmailFromToken(String token, Map<String, Object> claims);

  String validateAndGetEmailFromToken(String token);
}
