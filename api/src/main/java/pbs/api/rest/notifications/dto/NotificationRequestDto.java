package pbs.api.rest.notifications.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NotificationRequestDto {

  private String notificationType;
  private ArrayList<String> notificationValues;

}
