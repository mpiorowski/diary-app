package pbs.api.domain.notifications;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pbs.api.config.AppConstants;
import pbs.api.domain.generic.GenericService;
import pbs.api.security.SystemUser;
import pbs.api.utils.UtilsStringConversions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService extends GenericService<NotificationEntity, NotificationDao> {
  public NotificationService(NotificationDao dao) {
    super(dao);
  }

  @Override
  public List<NotificationEntity> findAll() {
    SystemUser user = currentUser();
    if (Boolean.TRUE.equals(checkRole(AppConstants.RolesString.ROLE_ADMIN))) {
      return dao.findAllByType("diary-answer");
    } else if (Boolean.TRUE.equals(checkRole(AppConstants.RolesString.ROLE_USER))) {
      return dao.findByType("diary-question", user.getUserId());
    }
    return Collections.emptyList();
  }

  @Transactional(readOnly = true)
  public List<NotificationEntity> findByType(String type) {
    SystemUser userDetails = currentUser();
    return dao.findByType(type, userDetails.getUserId());
  }

  @Override
  public Optional<NotificationEntity> findByUid(String uid) {
    UUID uuid = UtilsStringConversions.uidDecode(uid);
    return dao.findByUid(uuid);
  }

  @Override
  public String add(NotificationEntity entity) {
    UUID uuid = dao.add(entity);
    return UtilsStringConversions.uidEncode(uuid);
  }

  @Override
  public boolean edit(NotificationEntity entity) {
    return dao.edit(entity) == 1;
  }

  @Transactional
  public boolean read(String uid) {
    UUID uuid = UtilsStringConversions.uidDecode(uid);
    return dao.read(uuid) == 1;
  }

  @Override
  public boolean delete(String uid) {
    UUID uuid = UtilsStringConversions.uidDecode(uid);
    return dao.delete(uuid) == 1;
  }
}
