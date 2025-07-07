package idv.po.mtk_src.management.domain.user;

import idv.po.mtk_src.member.domain.Member;
import idv.po.mtk_src.member.domain.MemberRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMemberDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final RedisTemplate<String, UserDetails> userDetailsRedisTemplate;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    String key = "member_details:" + email;
    UserDetails cached = userDetailsRedisTemplate.opsForValue().get(key);

    if (cached != null) {
      return cached;
    }

    Member member =
        memberRepository
            .findMemberByMemberEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

    userDetailsRedisTemplate.opsForValue().set(key, member, Duration.ofMinutes(30)); // 快取 30 分鐘

    return member;
  }
}
