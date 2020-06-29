package pbs.api.domain.operations;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import pbs.api.domain.generic.GenericDao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Mapper
public interface OperationsDao extends GenericDao<OperationEntity> {

  @Override
  @Select("select * from operations where deleted is false order by operation_priority asc")
  List<OperationEntity> findAll();

  @Override
  @Select("select * from operations where deleted is false and uid = #{uid}")
  Optional<OperationEntity> findByUid(UUID uid);

  @Override
  @Select(
      "insert into operations "
          + "(operation_name, operation_priority) "
          + "values "
          + "(#{operationName}, #{operationPriority}) "
          + "returning uid")
  UUID add(OperationEntity entity);

  @Override
  @Update(
      "update operations set "
          + "operation_name = #{operationName}, "
          + "operation_priority = #{operationPriority} "
          + ") "
          + "where deleted is false and uid = #{uid}")
  int edit(OperationEntity entity);

  @Override
  @Update("update operations set deleted = true where deleted is false and uid = #{uid} ")
  int delete(UUID uid);
}
