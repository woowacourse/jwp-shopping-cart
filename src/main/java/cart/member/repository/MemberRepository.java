package cart.member.repository;

import cart.member.domain.Member;
import cart.member.domain.MemberId;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    MemberId save(final Member member);

    Optional<Member> findByMemberId(final MemberId memberId);

    Optional<Member> findByEmail(final String email);

    List<Member> findAll();

    MemberId deleteByMemberId(final MemberId memberId);
}
