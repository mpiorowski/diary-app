package pbs.api.rest.diaries.dto;

import pbs.api.rest.generic.GenericResponseDto;
import pbs.api.rest.users.dto.UserDataDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DiaryResponseDto extends GenericResponseDto {

  private String diaryType;
  private String diaryDescription;
  private Double diaryLike;
  private Integer diaryAmount;
  private String diaryDate;
  private UserDataDto diaryAuthor;
  private String answer;
  private String question;
  private Boolean notification;
}
