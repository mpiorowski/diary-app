package pbs.api.domain.user;

import org.springframework.stereotype.Service;
import pbs.api.domain.diaries.DiaryEntity;

import java.util.List;
import java.util.Set;

@Service
public class UsersService {

  private UserDao dao;

  public UsersService(UserDao dao) {
    this.dao = dao;
  }

  public List<UserEntity> findAll() {
    return dao.findAllUser();
  }
}
