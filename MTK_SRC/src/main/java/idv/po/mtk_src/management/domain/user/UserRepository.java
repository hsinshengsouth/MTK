package idv.po.mtk_src.management.domain.user;

import java.util.Optional;

public interface UserRepository {
  Optional<User> findByUserName(String userName);

  Optional<User> findByUserEmail(String userEmail);

  Optional<User> findByUserId(Integer userId);

  User persistUser(User user);

  Integer updateStatus(Integer userId, String userStatus);
}
