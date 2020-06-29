package pbs.api.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import pbs.api.config.AppConstants;
import pbs.api.security.SystemUser;

public class UtilsRoleCheck {

  public static boolean isRole(AppConstants.RoleName role) {
    SystemUser user =
        (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return user.getAuthorities().contains(new SimpleGrantedAuthority(role.toString()));
  }
}
