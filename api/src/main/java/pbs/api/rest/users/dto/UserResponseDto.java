package pbs.api.rest.users.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pbs.api.rest.generic.GenericResponseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponseDto extends GenericResponseDto {
  private String userName;
  private String userEmail;
  private String userRole;
}
