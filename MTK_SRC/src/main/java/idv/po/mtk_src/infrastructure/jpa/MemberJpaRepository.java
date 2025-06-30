package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.member.domain.Member;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

  Optional<Member> findByMemberEmail(String email);
}
