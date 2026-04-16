package org.viora.viorastreamingcore.common.configs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.service.GetUserAccountUseCase;

@Service
@RequiredArgsConstructor
public class VioraUserDetailsService implements UserDetailsService {

  private final GetUserAccountUseCase getUserAccountUseCase;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getUserAccountUseCase.findAccountByLogin(username);
  }
}
