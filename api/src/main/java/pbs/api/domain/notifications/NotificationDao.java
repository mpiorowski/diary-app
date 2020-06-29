package pbs.api.domain.notifications;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import pbs.api.domain.generic.GenericDao;
import pbs.api.domain.user.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Mapper
public interface NotificationDao extends GenericDao<NotificationEntity> {

  @Override
  @Select("select * from notifications where deleted is false")
  List<NotificationEntity> findAll();

  @Select(
      "select * from notifications where deleted is false and notification_type = #{type} order by created_at desc")
  @Results({
      @Result(
          property = "notificationAuthor",
          column = "fk_user_id",
          javaType = UserEntity.class,
          one = @One(select = "selectUser"))
  })
  List<NotificationEntity> findAllByType(@Param("type") String type);

  @Select(
      "select * from notifications where deleted is false and notification_type = #{type} and fk_user_id = #{userId} order by created_at desc")
  @Results({
      @Result(
          property = "notificationAuthor",
          column = "fk_user_id",
          javaType = UserEntity.class,
          one = @One(select = "selectUser"))
  })
  List<NotificationEntity> findByType(@Param("type") String type, @Param("userId") Long userId);

  @Override
  @Select("select * from notifications where deleted is false and  uid = #{uid}")
  Optional<NotificationEntity> findByUid(UUID uid);

  @Override
  @Select({
    "insert into notifications (notification_type, notification_values, fk_user_id)",
    "values (#{notificationType}, #{notificationValues}, #{userId})",
    "returning uid"
  })
  UUID add(NotificationEntity entity);

  @Override
  @Update({
    "update notifications set",
    "notification_type = #{notificationType}",
    "notification_values = #{notificationValues}",
    "where deleted is false and uid = #{uid}"
  })
  int edit(NotificationEntity entity);

  @Update({"update notifications set is_read = true where deleted is false and uid = #{uid}"})
  int read(UUID uid);

  @Override
  @Delete({"update notifications set deleted = true where deleted is false and uid = #{uid}"})
  int delete(UUID uid);
}
