package idv.po.mtk_src.member.infra;

import idv.po.mtk_src.infrastructure.jpa.MemberJpaRepository;
import idv.po.mtk_src.member.domain.member.Member;
import idv.po.mtk_src.member.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

  private final MemberJpaRepository memberJpaRepository;

  @Override
  public Member persistMember(Member member) {
    return memberJpaRepository.save(member);
  }

  @Override
  public Optional<Member> findMemberById(UUID id) {
    return memberJpaRepository.findById(id);
  }

  @Override
  public Optional<Member> findMemberByMemberEmail(String email) {
    return memberJpaRepository.findByMemberEmail(email);
  }
}
