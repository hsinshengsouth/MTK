package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.management.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ManageUserJpaRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUserEmail(String email);

  Optional<User> findByUserName(String username);

  @Modifying
  @Query("UPDATE User u SET u.userStatus = :userStatus WHERE u.userId = :userId")
  Integer updateStatus(@Param("userId") Integer userId, @Param("userStatus") String userStatus);
}
