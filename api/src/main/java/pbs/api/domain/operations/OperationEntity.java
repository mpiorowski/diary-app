package pbs.api.domain.operations;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pbs.api.domain.generic.GenericEntity;
import pbs.api.domain.user.UserEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class OperationEntity extends GenericEntity {
  private String operationName;
  private Integer operationPriority;
}
