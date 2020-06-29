package pbs.api.rest.users.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserSummaryDto {

  private String userName;
  private String userEmail;
  private Collection<? extends GrantedAuthority> authorities;

  public UserSummaryDto(
      String userName, String userEmail, Collection<? extends GrantedAuthority> authorities) {
    this.userName = userName;
    this.userEmail = userEmail;
    this.authorities = authorities;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }
}
