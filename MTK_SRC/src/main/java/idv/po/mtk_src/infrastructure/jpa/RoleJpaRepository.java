package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.management.domain.user.Role;
import idv.po.mtk_src.management.domain.user.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<Role, Integer>, RoleRepository {

    @Override
    Optional<Role> findByRoleId(Integer roleId);
}
