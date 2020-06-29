package pbs.api.domain.operations;

import org.springframework.stereotype.Service;
import pbs.api.domain.generic.GenericService;
import pbs.api.utils.UtilsStringConversions;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OperationService extends GenericService<OperationEntity, OperationsDao> {
  public OperationService(OperationsDao dao) {
    super(dao);
  }

  @Override
  public List<OperationEntity> findAll() {
    return dao.findAll();
  }

  @Override
  public Optional<OperationEntity> findByUid(String uid) {
    UUID uuid = UtilsStringConversions.uidDecode(uid);
    return dao.findByUid(uuid);
  }

  @Override
  public String add(OperationEntity entity) {
    UUID uuid = dao.add(entity);
    return UtilsStringConversions.uidEncode(uuid);
  }

  @Override
  public boolean edit(OperationEntity entity) {
    return dao.edit(entity) == 1;
  }

  @Override
  public boolean delete(String uid) {
    UUID uuid = UtilsStringConversions.uidDecode(uid);
    return dao.delete(uuid) == 1;
  }
}
