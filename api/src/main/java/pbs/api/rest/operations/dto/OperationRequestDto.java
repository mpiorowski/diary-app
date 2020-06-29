package pbs.api.rest.operations.dto;

import lombok.Data;

@Data
public class OperationRequestDto {

  private String operationName;
  private String operationPriority;
}
