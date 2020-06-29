package pbs.api.rest.notifications;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import pbs.api.config.mapper.MappersConfig;
import pbs.api.domain.notifications.NotificationEntity;
import pbs.api.rest.notifications.dto.NotificationResponseDto;

@Mapper(config = MappersConfig.class)
@Service
public interface NotificationMapper {

  NotificationResponseDto entityToDto(NotificationEntity notificationEntity);
}
