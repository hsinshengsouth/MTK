package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.management.domain.user.ManageUser;
import idv.po.mtk_src.management.domain.user.ManageUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@EnableJpaRepositories
public interface ManageUserJpaRepository extends JpaRepository<ManageUser, Integer>, ManageUserRepository {

    Optional<ManageUser> findByUserName(String username);

    Optional<ManageUser> findByUserEmail(String email);

    Optional<ManageUser> findByUserId(Integer userId);

    ManageUser save(ManageUser user);


    @Modifying
    @Query("UPDATE ManageUser u SET u.userStatus = :userStatus WHERE u.userId = :userId")
    Integer updateStatus(@Param("userId") String  userId, @Param("userStatus") String userStatus);


}
