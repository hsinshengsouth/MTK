package idv.po.mtk_src.member.domain.member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Member persistMember(Member member);
    Optional<Member> findMemberById(UUID id);
    Optional<Member> findMemberByMemberEmail(String email);
}
