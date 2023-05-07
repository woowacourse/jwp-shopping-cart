package cart.business.repository;

import cart.business.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    List<Member> findAll();

    Optional<Integer> findAndReturnId(Member member);

    Optional<Member> findById(Integer memberId);
}
