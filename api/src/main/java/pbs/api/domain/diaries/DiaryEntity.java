package pbs.api.domain.diaries;

import pbs.api.domain.generic.GenericEntity;
import pbs.api.domain.user.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DiaryEntity extends GenericEntity {
  private String diaryType;
  private String diaryDescription;
  private Double diaryLike;
  private Integer diaryAmount;
  private LocalDate diaryDate;
  private UserEntity diaryAuthor;
  private String question;
  private String answer;
}
