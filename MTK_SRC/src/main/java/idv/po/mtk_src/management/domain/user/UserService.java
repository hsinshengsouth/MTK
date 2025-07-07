package idv.po.mtk_src.management.domain.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(@Qualifier("manageUserRepositoryImpl") UserRepository manageUserRepository) {
    this.userRepository = manageUserRepository;
  }

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByUserEmail(email);
  }

  public Optional<User> getUserById(Integer userId) {
    return userRepository.findByUserId(userId);
  }

  public Optional<User> getUserByName(String userName) {
    return userRepository.findByUserName(userName);
  }

  public User saveManageUser(User user) {
    return userRepository.persistUser(user);
  }
}
