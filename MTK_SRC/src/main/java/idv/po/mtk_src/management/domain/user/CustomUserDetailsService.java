package idv.po.mtk_src.management.domain.user;

import idv.po.mtk_src.member.domain.Member;
import idv.po.mtk_src.member.domain.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userrepository;
  private final MemberRepository memberrepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<User> userOpt = userrepository.findByUserEmail(email);
    if (userOpt.isPresent()) {
      return userOpt.get();
    }

    Optional<Member> memberOpt = memberrepository.findMemberByMemberEmail(email);
    if (memberOpt.isPresent()) {
      return memberOpt.get();
    }

    throw new UsernameNotFoundException("User and Member not found" + email);
  }
}
