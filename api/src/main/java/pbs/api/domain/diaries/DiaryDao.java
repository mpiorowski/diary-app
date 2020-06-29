package pbs.api.domain.diaries;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import pbs.api.domain.generic.GenericDao;
import pbs.api.domain.user.UserEntity;
import pbs.api.rest.diaries.DiaryFilters;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Mapper
public interface DiaryDao extends GenericDao<DiaryEntity> {

  @Override
  @Select({
    "select * from diaries where deleted is false",
    "order by",
    "created_at desc",
  })
  @Results({
    @Result(
        property = "diaryAuthor",
        column = "fk_user_id",
        javaType = UserEntity.class,
        one = @One(select = "selectUser"))
  })
  List<DiaryEntity> findAll();

  @Select(
      "select * from diaries where deleted is false and fk_user_id = #{userId} order by created_at desc")
  @Results({
    @Result(
        property = "diaryAuthor",
        column = "fk_user_id",
        javaType = UserEntity.class,
        one = @One(select = "selectUser"))
  })
  List<DiaryEntity> findAllByUserId(Long userId);

  @Select({
    "<script>",
    "select * from diaries",
    "where deleted is false",
    "<if test='filter.date != null'>",
    "and diary_date &gt;= TO_DATE(#{filter.date}, 'YYYY-MM-DD')",
    "and diary_date &lt; TO_DATE(#{filter.date}, 'YYYY-MM-DD') + interval '1 day'",
    "</if>",
    "<if test='filter.userId != null'>",
    "and fk_user_id = #{filter.userId}",
    "</if>",
    "<if test='filter.notification != null'>",
    "and notification = #{filter.notification}",
    "</if>",
    "order by ",
    "<if test='filter.orderBy != null'>",
    "${filter.orderBy} desc, ",
    "</if>",
    "created_at desc",
    "</script>"
  })
  @Results({
    @Result(
        property = "diaryAuthor",
        column = "fk_user_id",
        javaType = UserEntity.class,
        one = @One(select = "selectUser"))
  })
  List<DiaryEntity> findByFilter(@Param("filter") DiaryFilters filter);

  @Select({
    "select * from diaries",
    "where deleted is false",
    "and notification = #{filter.notification}",
    "and fk_user_id = #{filter.userId}",
    "order by created_at desc"
  })
  List<DiaryEntity> findByNotification(@Param("filter") DiaryFilters filter);

  @Select({
    "select * from diaries",
    "where deleted is false",
    "and diary_date >= #{startDate} and diary_date <= #{endDate}",
    "order by fk_user_id asc, diary_date asc"
  })
  @Results({
      @Result(
          property = "diaryAuthor",
          column = "fk_user_id",
          javaType = UserEntity.class,
          one = @One(select = "selectUser"))
  })
  List<DiaryEntity> findByDates(
      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Override
  @Select("select * from diaries where deleted is false and uid = #{uid}")
  @Results({
    @Result(
        property = "diaryAuthor",
        column = "fk_user_id",
        javaType = UserEntity.class,
        one = @One(select = "selectUser"))
  })
  Optional<DiaryEntity> findByUid(UUID uid);

  @Override
  @Select(
      "insert into diaries "
          + "(diary_type, diary_description, diary_like, diary_amount, diary_date, fk_user_id) "
          + "values "
          + "(#{diaryType}, #{diaryDescription}, #{diaryLike}, #{diaryAmount}, #{diaryDate}, #{diaryAuthor.id}) "
          + "returning uid")
  UUID add(DiaryEntity entity);

  @Override
  @Update(
      "update diaries set "
          + "diary_type = #{diaryType}, "
          + "diary_description = #{diaryDescription}, "
          + "diary_like = #{diaryLike}, "
          + "diary_amount = #{diaryAmount}, "
          + "answer = #{answer}, "
          + "diary_date = #{diaryDate} "
          + "where deleted is false and uid = #{uid}")
  int edit(DiaryEntity entity);

  @Update(
      "update diaries set " + "question = #{question} " + "where deleted is false and uid = #{uid}")
  int editQuestion(DiaryEntity entity);

  @Update("update diaries set active = not active where deleted is false and uid = #{uid} ")
  int changeStatus(UUID uid);

  @Override
  @Update("update diaries set deleted = true where deleted is false and uid = #{uid} ")
  int delete(UUID uid);
}
