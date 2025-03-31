package idv.po.mtk_src.management.domain.repository;

import idv.po.mtk_src.management.domain.user.ManageUser;
import idv.po.mtk_src.management.domain.user.ManageUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaManageUserRepository  extends JpaRepository<ManageUser, Integer>, ManageUserRepository {

    Optional<ManageUser> findByUserName(String username);

    Optional<ManageUser> findByUserEmail(String email);

    Optional<ManageUser> findByUserId(Integer userId);

    ManageUser save(ManageUser user);

    ManageUser update(ManageUser user);
}
