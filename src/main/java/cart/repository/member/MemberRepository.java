package cart.repository.member;

import cart.domain.member.Member;
import cart.domain.member.MemberId;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    MemberId save(final Member member);
    Optional<Member> findByMemberId(final MemberId memberId);
    List<Member> findAll();
    MemberId deleteByMemberId(final MemberId memberId);
}
