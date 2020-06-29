package pbs.api.rest.operations;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import pbs.api.config.mapper.MappersConfig;
import pbs.api.domain.operations.OperationEntity;
import pbs.api.rest.operations.dto.OperationResponseDao;
import pbs.api.rest.operations.dto.OperationRequestDto;

@Mapper(config = MappersConfig.class)
@Service
public interface OperationMapper {

  OperationResponseDao entityToDto(OperationEntity entity);

  OperationEntity dtoToEntity(OperationRequestDto dto);
}
