package idv.po.mtk_src.infrastructure.jpa;

import idv.po.mtk_src.member.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

  Optional<Member> findByMemberEmail(String email);
}
