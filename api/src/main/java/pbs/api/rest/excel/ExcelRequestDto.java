package pbs.api.rest.excel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExcelRequestDto {
  private LocalDate startDate;
  private LocalDate endDate;
}
