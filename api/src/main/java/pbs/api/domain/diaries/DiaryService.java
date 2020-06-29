package pbs.api.domain.diaries;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pbs.api.config.AppConstants;
import pbs.api.domain.generic.GenericService;
import pbs.api.domain.notifications.NotificationEntity;
import pbs.api.domain.notifications.NotificationService;
import pbs.api.rest.diaries.DiaryFilters;
import pbs.api.security.SystemUser;
import pbs.api.utils.UtilsString;
import pbs.api.utils.UtilsStringConversions;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pbs.api.utils.UtilsRoleCheck.isRole;

@Service
public class DiaryService extends GenericService<DiaryEntity, DiaryDao> {

  private final NotificationService notificationService;

  public DiaryService(DiaryDao dao, NotificationService notificationService) {
    super(dao);
    this.notificationService = notificationService;
  }

  @Override
  public List<DiaryEntity> findAll() {
    SystemUser userDetails = currentUser();
    if (isRole(AppConstants.RoleName.ROLE_ADMIN) || isRole(AppConstants.RoleName.ROLE_CLIENT)) {
      return dao.findAll();
    } else if (userDetails
        .getAuthorities()
        .contains(new SimpleGrantedAuthority(AppConstants.RolesString.ROLE_USER))) {
      return dao.findAllByUserId(userDetails.getUserId());
    } else return Collections.emptyList();
  }

  public List<DiaryEntity> findByFilter(DiaryFilters filter) {
    SystemUser userDetails = currentUser();
    if (isRole(AppConstants.RoleName.ROLE_ADMIN) || isRole(AppConstants.RoleName.ROLE_CLIENT)) {
      return dao.findByFilter(filter);
    }
    filter.setUserId(userDetails.getUserId());
    return dao.findByFilter(filter);
  }

  public List<DiaryEntity> findByNotification(DiaryFilters filter) {
    SystemUser userDetails = currentUser();
    filter.setUserId(userDetails.getUserId());
    return dao.findByNotification(filter);
  }

  public List<DiaryEntity> findByDates(LocalDate startDate, LocalDate endDate) {
    return dao.findByDates(startDate, endDate);
  }

  @Override
  public Optional<DiaryEntity> findByUid(String uid) {
    UUID uuid = UtilsStringConversions.uidDecode(uid);
    return dao.findByUid(uuid);
  }

  @Override
  public String add(DiaryEntity entity) {
    UUID uuid = dao.add(entity);
    return UtilsStringConversions.uidEncode(uuid);
  }

  @Override
  public boolean edit(DiaryEntity entity) {
    Optional<DiaryEntity> diary = dao.findByUid(entity.getUid());
    if (diary.isPresent()
        && UtilsString.isBlankString(diary.get().getAnswer())
        && !UtilsString.isBlankString(entity.getAnswer())) {
      var notificationEntity = new NotificationEntity();
      notificationEntity.setUserId(diary.get().getDiaryAuthor().getId());
      notificationEntity.setNotificationType("diary-answer");
      notificationEntity.setNotificationValues(
          List.of(
              UtilsStringConversions.uidEncode(diary.get().getUid()),
              diary.get().getDiaryDate().toString()));
      notificationService.add(notificationEntity);
    }
    return dao.edit(entity) == 1;
  }

  @Transactional
  public boolean editQuestion(DiaryEntity entity) {

    Optional<DiaryEntity> diary = dao.findByUid(entity.getUid());
    if (diary.isPresent() && UtilsString.isBlankString(diary.get().getQuestion())) {
      var notificationEntity = new NotificationEntity();
      notificationEntity.setUserId(diary.get().getDiaryAuthor().getId());
      notificationEntity.setNotificationType("diary-question");
      notificationEntity.setNotificationValues(
          List.of(
              UtilsStringConversions.uidEncode(diary.get().getUid()),
              diary.get().getDiaryDate().toString()));
      notificationService.add(notificationEntity);
    }
    return dao.editQuestion(entity) == 1;
  }

  public boolean changeStatus(String uid) {
    UUID uuid = UtilsStringConversions.uidDecode(uid);
    return dao.changeStatus(uuid) == 1;
  }

  @Override
  public boolean delete(String uid) {
    UUID uuid = UtilsStringConversions.uidDecode(uid);
    return dao.delete(uuid) == 1;
  }
}
