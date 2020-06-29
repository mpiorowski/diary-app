package pbs.api.rest.diaries;

import com.github.pagehelper.PageHelper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbs.api.annotations.CurrentUser;
import pbs.api.domain.diaries.DiaryEntity;
import pbs.api.domain.diaries.DiaryService;
import pbs.api.domain.user.UserEntity;
import pbs.api.rest.diaries.dto.DiaryNotificationDto;
import pbs.api.rest.diaries.dto.DiaryRequestDto;
import pbs.api.rest.diaries.dto.DiaryRequestQuestionDto;
import pbs.api.rest.diaries.dto.DiaryResponseDto;
import pbs.api.rest.generic.GenericController;
import pbs.api.rest.generic.GenericUidDto;
import pbs.api.security.SystemUser;
import pbs.api.utils.UtilsStringConversions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diaries")
public class DiariesController
    extends GenericController<DiaryService, DiaryResponseDto, DiaryRequestDto> {

  private DiaryMapper mapper = Mappers.getMapper(DiaryMapper.class);

  protected DiariesController(DiaryService service) {
    super(service);
  }

  //  @Override
  @GetMapping()
  public ResponseEntity<List<DiaryResponseDto>> findAll(DiaryFilters filter) {
    if (filter.getLimit() != null) {
      PageHelper.offsetPage(0, filter.getLimit());
    }
    List<DiaryResponseDto> diaryDataDtoList =
        service.findByFilter(filter).stream().map(mapper::entityToDto).collect(Collectors.toList());
    return new ResponseEntity<>(diaryDataDtoList, HttpStatus.OK);
  }

  @GetMapping(params = {"date"})
  public ResponseEntity<List<DiaryResponseDto>> findByDate(DiaryFilters filter) {

    List<DiaryResponseDto> diaryDataDtoList =
        service.findByFilter(filter).stream().map(mapper::entityToDto).collect(Collectors.toList());
    return new ResponseEntity<>(diaryDataDtoList, HttpStatus.OK);
  }

  @GetMapping(params = {"notification"})
  public ResponseEntity<List<DiaryNotificationDto>> findByNotification(DiaryFilters filter) {

    List<DiaryNotificationDto> notificationDtoList =
        service.findByNotification(filter).stream()
            .map(mapper::entityToNotificationDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(notificationDtoList, HttpStatus.OK);
  }

  @Override
  @GetMapping("/{uid}")
  public ResponseEntity<DiaryResponseDto> findByUid(@PathVariable("uid") String uid) {
    try {
      Optional<DiaryEntity> diaryEntity = service.findByUid(uid);
      if (diaryEntity.isPresent()) {
        DiaryResponseDto diaryResponseDto = mapper.entityToDto(diaryEntity.get());
        return new ResponseEntity<>(diaryResponseDto, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      logger.error("Diary findByUid: ", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  @PostMapping()
  public ResponseEntity<GenericUidDto> add(
      @RequestBody DiaryRequestDto newDto, @CurrentUser SystemUser systemUser) {
    try {

      UserEntity userEntity = new UserEntity();
      userEntity.setId(systemUser.getUserId());
      DiaryEntity diaryEntity = mapper.dtoToEntity(newDto);
      diaryEntity.setDiaryAuthor(userEntity);

      GenericUidDto genericUidDto = new GenericUidDto(service.add(diaryEntity));
      return new ResponseEntity<>(genericUidDto, HttpStatus.CREATED);
    } catch (Exception e) {
      logger.error("Diary add: ", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  @PutMapping("/{uid}")
  public ResponseEntity<Boolean> edit(
      @PathVariable("uid") String uid, @RequestBody DiaryRequestDto editDto) {
    try {
      DiaryEntity diaryEntity = mapper.dtoToEntity(editDto);
      diaryEntity.setUid(UtilsStringConversions.uidDecode(uid));
      if (service.edit(diaryEntity)) {
        return new ResponseEntity<>(true, HttpStatus.CREATED);
      }
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      logger.error("Diary edit: ", e);
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/{uid}/questions")
  public ResponseEntity<Boolean> editQuestion(
      @PathVariable("uid") String uid, @RequestBody DiaryRequestQuestionDto editDto) {
    try {
      DiaryEntity diaryEntity = mapper.dtoToEntity(editDto);
      diaryEntity.setUid(UtilsStringConversions.uidDecode(uid));
      if (service.editQuestion(diaryEntity)) {
        return new ResponseEntity<>(true, HttpStatus.CREATED);
      }
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      logger.error("Diary edit: ", e);
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/{uid}")
  public ResponseEntity<Boolean> changeStatus(@PathVariable("uid") String uid) {
    try {
      if (service.changeStatus(uid)) {
        return new ResponseEntity<>(true, HttpStatus.OK);
      }
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      logger.error("Diary change status: ", e);
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  @DeleteMapping("/{uid}")
  public ResponseEntity<Boolean> delete(@PathVariable("uid") String uid) {
    try {
      if (service.delete(uid)) {
        return new ResponseEntity<>(true, HttpStatus.OK);
      }
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      logger.error("Diary delete: ", e);
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }
}
