package idv.po.mtk_src.infrastructure.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private static final String PREFIX = "login:token:";

    private final RedisTemplate<String, String> redisTemplate;

    public void cacheToken(String token, String userEmail, long ttlMillis) {
        redisTemplate.opsForValue().set(PREFIX + token, userEmail, Duration.ofMillis(ttlMillis));
    }

    public boolean isValidToken(String token) {
        return redisTemplate.hasKey(PREFIX + token);
    }

    public void removeToken(String token) {
        redisTemplate.delete(PREFIX + token);
    }









}
