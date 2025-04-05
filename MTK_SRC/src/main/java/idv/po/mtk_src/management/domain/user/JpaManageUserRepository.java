package idv.po.mtk_src.management.domain.user;

import idv.po.mtk_src.infrastructure.repository.SpringDataJpaManageUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaManageUserRepository  implements  ManageUserRepository{

    private final SpringDataJpaManageUserRepository manageUserRepo;


    @Override
    public Optional<ManageUser> findByUserName(String userName) {
        return manageUserRepo.findByUserName(userName);
    }

    @Override
    public Optional<ManageUser> findByUserEmail(String userEmail) {
        return manageUserRepo.findByUserEmail(userEmail);
    }

    @Override
    public Optional<ManageUser> findByUserId(Integer userId) {
        return manageUserRepo.findByUserId(userId) ;
    }

    @Override
    public ManageUser save(ManageUser user) {
        return manageUserRepo.save(user);
    }

    @Override
    public Integer updateStatus(String userId, String userStatus) {
        return manageUserRepo.updateStatus(userId,userStatus);
    }
}
