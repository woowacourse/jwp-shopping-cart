package cart.repository;

import cart.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    long save(final Member member);
    Optional<Member> findByMemberId(final long memberId);
    List<Member> findAll();
    long deleteByMemberId(final long memberId);
}
