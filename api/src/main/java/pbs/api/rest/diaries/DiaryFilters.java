package pbs.api.rest.diaries;

import lombok.Data;
import pbs.api.rest.generic.GenericFilter;

@Data
public class DiaryFilters extends GenericFilter {

  private Long userId;

//  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern='YYYY-MM-DDTHH:mm:ss.SSSSZ')
  private String date;

  private Boolean notification;
}
