package pbs.api.domain.notifications;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pbs.api.domain.generic.GenericEntity;
import pbs.api.domain.user.UserEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationEntity extends GenericEntity {

  private String notificationType;
  private List<String> notificationValues;
  private UserEntity notificationAuthor;
  private Long userId;
  private Boolean isRead;
}
