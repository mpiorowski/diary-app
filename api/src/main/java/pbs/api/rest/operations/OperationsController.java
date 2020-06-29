package pbs.api.rest.operations;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbs.api.annotations.CurrentUser;
import pbs.api.domain.operations.OperationEntity;
import pbs.api.domain.operations.OperationService;
import pbs.api.rest.generic.GenericController;
import pbs.api.rest.generic.GenericUidDto;
import pbs.api.rest.operations.dto.OperationRequestDto;
import pbs.api.rest.operations.dto.OperationResponseDao;
import pbs.api.security.SystemUser;
import pbs.api.utils.UtilsStringConversions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operations")
public class OperationsController
    extends GenericController<OperationService, OperationResponseDao, OperationRequestDto> {

  private OperationMapper mapper = Mappers.getMapper(OperationMapper.class);

  protected OperationsController(OperationService service) {
    super(service);
  }

//  @Override
  @GetMapping()
  public ResponseEntity<List<OperationResponseDao>> findAll() {
    List<OperationResponseDao> diaryDataDaoList =
        service.findAll().stream().map(mapper::entityToDto).collect(Collectors.toList());
    return new ResponseEntity<>(diaryDataDaoList, HttpStatus.OK);
  }

  @Override
  @GetMapping("/{uid}")
  public ResponseEntity<OperationResponseDao> findByUid(@PathVariable("uid") String uid) {
    try {
      Optional<OperationEntity> entity = service.findByUid(uid);
      if (entity.isPresent()) {
        OperationResponseDao diaryDataDao = mapper.entityToDto(entity.get());
        return new ResponseEntity<>(diaryDataDao, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      logger.error("Operation findByUid: ", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  @PostMapping()
  public ResponseEntity<GenericUidDto> add(
      @RequestBody OperationRequestDto newDto, @CurrentUser SystemUser userDetails) {
    try {
      OperationEntity entity = mapper.dtoToEntity(newDto);
      GenericUidDto genericUidDto = new GenericUidDto(service.add(entity));
      return new ResponseEntity<>(genericUidDto, HttpStatus.CREATED);
    } catch (Exception e) {
      logger.error("Operation add: ", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  @PutMapping("/{uid}")
  public ResponseEntity<Boolean> edit(
      @PathVariable("uid") String uid, @RequestBody OperationRequestDto editDto) {
    try {
      OperationEntity entity = mapper.dtoToEntity(editDto);
      entity.setUid(UtilsStringConversions.uidDecode(uid));
      if (service.edit(entity)) {
        return new ResponseEntity<>(true, HttpStatus.CREATED);
      }
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      logger.error("Operation edit: ", e);
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
      logger.error("Operation delete: ", e);
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
  }
}
