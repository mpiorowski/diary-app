package pbs.api.domain.user;

import pbs.api.config.AppConstants;
import pbs.api.domain.generic.GenericEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends GenericEntity {

  @NotBlank
  @Size(max = 40)
  private String userName;

  @NotBlank
  @Email
  @Size(max = 40)
  private String userEmail;

  @NotBlank
  @Size(max = 40)
  private String userPassword;

  @NotNull private AppConstants.RoleName userRole;

}
