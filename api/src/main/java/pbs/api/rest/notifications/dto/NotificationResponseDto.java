package pbs.api.rest.notifications.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pbs.api.rest.generic.GenericResponseDto;
import pbs.api.rest.users.dto.UserDataDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationResponseDto extends GenericResponseDto {
  private String notificationType;
  private List<String> notificationValues;
  private UserDataDto notificationAuthor;
  private Boolean isRead;
}
