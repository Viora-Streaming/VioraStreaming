package org.viora.viorastreamingcore.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails, CredentialsContainer {

  @Getter
  private Long id;
  private String login;
  private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.login;
  }

  @Override
  public void eraseCredentials() {
    this.password = "";
  }
}
