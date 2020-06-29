package pbs.api.domain.sys;

import pbs.api.domain.user.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AuthDao {

  @Select(
      "select * from sys_users where ( user_name = #{userNameOrEmail} or user_email = #{userNameOrEmail} )")
  UserEntity authUserByNameOrEmail(String userNameOrEmail);

  @Select("select * from sys_users where id = #{userId}")
  UserEntity authUserById(Long userId);
}
