package idv.po.mtk_src.management.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManageUserService {

    private final ManageUserRepository userRepository;

    public ManageUserService(
            @Qualifier("jpaManageUserRepository") ManageUserRepository manageUserRepository
    ) {
        this.userRepository = manageUserRepository;
    }
    public Optional<ManageUser> getUserByEmail(String email) {
         return userRepository.findByUserEmail(email);
    }

    public Optional<ManageUser> getUserById(Integer userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<ManageUser> getUserByName(String  userName) {
        return userRepository.findByUserName(userName);
    }

    public ManageUser saveManageUser(ManageUser user) {
        return userRepository.save(user);
    }



}
