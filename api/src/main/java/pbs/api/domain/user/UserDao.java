package pbs.api.domain.user;

import pbs.api.rest.users.dto.UserAddRequestDto;
import pbs.api.rest.users.dto.UserEditRequestDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface UserDao {

  @Select("select * from sys_users where user_role = 'ROLE_USER' and deleted is false")
  List<UserEntity> findAllUser();

  @Select(
      "SELECT "
          + "user_id as userId, "
          + "user_name as userName, "
          + "user_email as userEmail, "
          + "user_password as userPassword "
          + "FROM sys_users "
          + "WHERE ( user_name = #{userNameOrEmail} OR user_email = #{userNameOrEmail} )")
  UserEntity selectUserByUserNameOrEmail(String userNameOrEmail);

  @Select(
      "SELECT "
          + "user_id as userId, "
          + "user_name as userName, "
          + "user_email as userEmail, "
          + "user_password as userPassword "
          + "FROM sys_users "
          + "WHERE user_id = #{userId}")
  UserEntity selectUserByUserId(Long userId);

  @Select(
      "SELECT "
          + "user_id as userId, "
          + "user_name as userName, "
          + "user_email as userEmail, "
          + "user_role as userRole "
          + "FROM sys_users ")
  Set<UserEntity> selectAllUsers();

  @RolesAllowed("ROLE_ADMIN")
  @Select({
    "INSERT INTO sys_users(user_name, user_email, user_password, fk_role_id) "
        + "VALUES("
        + "#{userName}, "
        + "#{userEmail}, "
        + "crypt(#{userPassword}, gen_salt('bf', 8)), "
        + "(SELECT role_id FROM sys_roles where role_desc = #{userRole}) "
        + ") RETURNING user_id"
  })
  int addUser(UserAddRequestDto user);

  @RolesAllowed("ROLE_ADMIN")
  @Update({
    "UPDATE sys_users set "
        + "user_name = #{userName}, "
        + "user_email = #{userEmail}, "
        + "fk_role_id = (SELECT role_id from sys_roles where role_desc = #{userRole}) "
        + "where user_id = #{userId}"
  })
  void updateUser(UserEditRequestDto user);

  @RolesAllowed("ROLE_ADMIN")
  @Delete({"DELETE FROM sys_users where user_id = #{userId}"})
  void deleteUser(int userId);
}
