package pbs.api.rest.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import pbs.api.security.SystemUser;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericController<S, N, R> {

  // TODO - delete mock user
  protected SystemUser mockUser() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    return new SystemUser(3L, "mat", "mat@gmail.com", "pass", authorities);
  }

  protected SystemUser currentUser() {
    return (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  protected static final Logger logger = LoggerFactory.getLogger(GenericController.class);

  protected S service;

  protected GenericController(S service) {
    this.service = service;
  }

//  public abstract ResponseEntity<List<N>> findAll();

  public abstract ResponseEntity<N> findByUid(String uid);

  public abstract ResponseEntity<GenericUidDto> add(R responseDto, SystemUser userDetails);

  public abstract ResponseEntity<Boolean> edit(String uid, R responseDto);

  public abstract ResponseEntity<Boolean> delete(String uid);
}
