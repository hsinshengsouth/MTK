package idv.po.mtk_src.infrastructure.repository;

import idv.po.mtk_src.management.domain.user.ManageUser;
import idv.po.mtk_src.management.domain.user.ManageUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ManageUserJpaRepository extends JpaRepository<ManageUser, Integer> {

    Optional<ManageUser> findByUserEmail(String email);

    Optional<ManageUser> findByUserName(String username);
    @Modifying
    @Query("UPDATE ManageUser u SET u.userStatus = :userStatus WHERE u.userId = :userId")
    Integer updateStatus(@Param("userId") Integer   userId, @Param("userStatus") String userStatus);


}
