package idv.po.mtk_src.management.infra;

import idv.po.mtk_src.infrastructure.jpa.RoleJpaRepository;
import idv.po.mtk_src.management.domain.user.Role;
import idv.po.mtk_src.management.domain.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

   private final RoleJpaRepository roleRepository;

    @Override
    public Optional<Role> findByRoleId(Integer roleId) {
        return roleRepository.findByRoleId(roleId);
    }
}
