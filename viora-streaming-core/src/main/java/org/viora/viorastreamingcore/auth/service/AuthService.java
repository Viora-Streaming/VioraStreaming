package org.viora.viorastreamingcore.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.auth.dto.LoginUserRequest;
import org.viora.viorastreamingcore.auth.exception.InvalidCredentialsException;
import org.viora.viorastreamingcore.common.configs.security.JwtTokenService;

@Service
@RequiredArgsConstructor
public class AuthService implements LoginUserUseCase {

  private final JwtTokenService jwtTokenService;
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public String loginUser(LoginUserRequest request) {
    UserDetails account = userDetailsService.loadUserByUsername(request.login());
    if (!passwordEncoder.matches(request.password(), account.getPassword())) {
      throw new InvalidCredentialsException("Invalid credentials");
    }

    return jwtTokenService.generateToken(account);
  }
}
