package pbs.api.rest;

import lombok.Data;

@Data
public class ClientErrorDto {

  private String message;
  private String stack;
}
