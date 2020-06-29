package pbs.api.domain.generic;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import pbs.api.security.SystemUser;

import java.util.List;
import java.util.Optional;

public abstract class GenericService<E, D> {

  protected D dao;

  public GenericService(D dao) {
    this.dao = dao;
  }

  protected SystemUser currentUser() {
    return (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  protected Boolean checkRole(String role) {
    var user = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return user.getAuthorities().contains(new SimpleGrantedAuthority(role));
  }

  @Transactional(readOnly = true)
  public abstract List<E> findAll();

  @Transactional(readOnly = true)
  public abstract Optional<E> findByUid(String uid);

  @Transactional
  public abstract String add(E entity);

  @Transactional
  public abstract boolean edit(E entity);

  @Transactional
  public abstract boolean delete(String uid);
}
