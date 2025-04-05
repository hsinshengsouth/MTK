package idv.po.mtk_src.management.domain.user;

import java.util.Optional;

public interface ManageUserRepository {
    Optional<ManageUser> findByUserName(String userName);
    Optional<ManageUser> findByUserEmail(String userEmail);
    Optional<ManageUser> findByUserId(Integer userId);
    ManageUser save(ManageUser user);
    Integer updateStatus(String  userId, String  userStatus);


}
