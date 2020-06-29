package pbs.api.rest.notifications;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbs.api.domain.notifications.NotificationEntity;
import pbs.api.domain.notifications.NotificationService;
import pbs.api.rest.generic.GenericController;
import pbs.api.rest.generic.GenericUidDto;
import pbs.api.rest.notifications.dto.NotificationRequestDto;
import pbs.api.rest.notifications.dto.NotificationResponseDto;
import pbs.api.security.SystemUser;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/notifications")
@RestController
public class NotificationsController
    extends GenericController<
        NotificationService, NotificationResponseDto, NotificationRequestDto> {

  private NotificationMapper mapper = Mappers.getMapper(NotificationMapper.class);

  protected NotificationsController(NotificationService service) {
    super(service);
  }

  //  @Override
  @GetMapping()
  public ResponseEntity<List<NotificationResponseDto>> findAll() {
    List<NotificationEntity> notificationEntities = service.findAll();

    if (!notificationEntities.isEmpty()) {
      List<NotificationResponseDto> notificationResponseDtos =
          notificationEntities.stream().map(mapper::entityToDto).collect(Collectors.toList());
      return new ResponseEntity<>(notificationResponseDtos, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<List<NotificationResponseDto>> findByType(NotificationsFilter filter) {

    List<NotificationResponseDto> notificationResponseDtoList =
        service.findByType(filter.getType()).stream()
            .map(mapper::entityToDto)
            .collect(Collectors.toList());
    return new ResponseEntity<>(notificationResponseDtoList, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<NotificationResponseDto> findByUid(String uid) {
    return null;
  }

  @Override
  public ResponseEntity<GenericUidDto> add(NotificationRequestDto newDto, SystemUser userDetails) {
    return null;
  }

  @Override
  public ResponseEntity<Boolean> edit(String uid, NotificationRequestDto editDto) {
    return null;
  }

  @PatchMapping("/{uid}")
  public ResponseEntity<Boolean> read(@PathVariable("uid") String uid) {
    if (service.read(uid)) {
      return new ResponseEntity<>(true, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
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
      logger.error("Notification delete: ", e);
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }
}
