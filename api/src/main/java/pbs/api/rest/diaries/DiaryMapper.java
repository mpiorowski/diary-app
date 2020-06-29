package pbs.api.rest.diaries;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;
import pbs.api.config.mapper.MappersConfig;
import pbs.api.domain.diaries.DiaryEntity;
import pbs.api.rest.diaries.dto.DiaryRequestDto;
import pbs.api.rest.diaries.dto.DiaryRequestQuestionDto;
import pbs.api.rest.diaries.dto.DiaryResponseDto;
import pbs.api.rest.diaries.dto.DiaryNotificationDto;

@Mapper(config = MappersConfig.class)
@Service
public interface DiaryMapper {

  @Mapping(target = "diaryAuthor.userName", source = "diaryAuthor.userName")
  @Mapping(target = "diaryAuthor.userEmail", source = "diaryAuthor.userEmail")
  DiaryResponseDto entityToDto(DiaryEntity diaryEntity);

  DiaryNotificationDto entityToNotificationDto(DiaryEntity diaryEntity);

  DiaryEntity dtoToEntity(DiaryRequestDto newDto);

  DiaryEntity dtoToEntity(DiaryRequestQuestionDto newDto);
}
