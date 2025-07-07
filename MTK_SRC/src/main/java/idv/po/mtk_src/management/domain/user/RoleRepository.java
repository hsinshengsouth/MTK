package idv.po.mtk_src.management.domain.user;

import java.util.Optional;

public interface RoleRepository {
  Optional<Role> findByRoleId(Integer roleId);
}
