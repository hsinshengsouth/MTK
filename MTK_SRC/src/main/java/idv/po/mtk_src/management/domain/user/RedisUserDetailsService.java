package idv.po.mtk_src.management.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class RedisUserDetailsService implements UserDetailsService {


    private final ManageUserRepository repository;
    private final RedisTemplate<String, UserDetails> userDetailsRedisTemplate;

    public RedisUserDetailsService(
            @Qualifier("manageUserRepositoryImpl") ManageUserRepository manageUserRepository, RedisTemplate<String, UserDetails> userDetailsRedisTemplate
    ) {
        this.repository = manageUserRepository;
        this.userDetailsRedisTemplate = userDetailsRedisTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        String key = "user_details:" + userEmail;
        UserDetails cached = userDetailsRedisTemplate.opsForValue().get(key);

        if (cached != null){
            return cached;
        }

        ManageUser user=repository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));;

        userDetailsRedisTemplate.opsForValue().set(key, user, Duration.ofMinutes(30)); // 快取 30 分鐘

        return user;
    }



}
