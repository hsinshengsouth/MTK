package idv.po.mtk_src.management.infra;

import idv.po.mtk_src.infrastructure.jpa.ManageUserJpaRepository;
import idv.po.mtk_src.management.domain.user.User;
import idv.po.mtk_src.management.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ManageUserRepositoryImpl implements UserRepository {

    private final ManageUserJpaRepository manageUserRepo;


    @Override
    public Optional<User> findByUserName(String userName) {
        return manageUserRepo.findByUserName(userName);
    }

    @Override
    public Optional<User> findByUserEmail(String userEmail) {
         return manageUserRepo.findByUserEmail(userEmail);
    }

    @Override
    public Optional<User> findByUserId(Integer userId) {
        return manageUserRepo.findById(userId) ;
    }

    @Override
    public User persistUser(User user) {
        return manageUserRepo.save(user);
    }

    @Override
    public Integer updateStatus(Integer userId, String userStatus) {
        return manageUserRepo.updateStatus(userId,userStatus);
    }
}
