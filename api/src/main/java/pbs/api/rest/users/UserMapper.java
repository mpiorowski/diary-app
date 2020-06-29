package pbs.api.rest.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;
import pbs.api.config.mapper.MappersConfig;
import pbs.api.domain.diaries.DiaryEntity;
import pbs.api.domain.user.UserEntity;
import pbs.api.rest.diaries.dto.DiaryNotificationDto;
import pbs.api.rest.diaries.dto.DiaryRequestDto;
import pbs.api.rest.diaries.dto.DiaryResponseDto;
import pbs.api.rest.users.dto.UserResponseDto;

@Mapper(config = MappersConfig.class)
@Service
public interface UserMapper {

  UserResponseDto entityToDto(UserEntity userEntity);

//  UserEntity dtoToEntity(UserRequestDto newDto);
}
