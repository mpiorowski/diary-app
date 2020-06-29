package pbs.api.rest.operations.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pbs.api.rest.generic.GenericResponseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class OperationResponseDao extends GenericResponseDto {

  private String operationName;
  private String operationPriority;

}
