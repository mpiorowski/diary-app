package pbs.api.rest.users.dto;

import pbs.api.rest.generic.GenericResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDataDto extends GenericResponseDto {

  private String userName;
  private String userEmail;

}
