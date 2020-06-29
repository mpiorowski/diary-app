package pbs.api.rest.users.dto;

import lombok.Data;

@Data
public class UserAddRequestDto {

  private int userId;
  private String userName;
  private String userEmail;
  private String userPassword;
  private String userRole;

}
