package pbs.api.rest.users.dto;

public class UserTableDto {

  private long userId;
  private String userName;
  private String userEmail;
  private String userRole;

  public UserTableDto(long userId, String userName, String userEmail, String userRole) {
    this.userId = userId;
    this.userName = userName;
    this.userEmail = userEmail;
    this.userRole = userRole;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
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

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }
}
