package pbs.api.rest.diaries.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiaryRequestDto {
  private String diaryType;
  private String diaryDescription;
  private Double diaryLike;
  private Integer diaryAmount;
  private LocalDate diaryDate;
  private String answer;
}
