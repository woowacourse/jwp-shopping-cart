package cart.repository.member;

import cart.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    List<Member> findAll();

    Optional<Member> findById(Long id);

    void save(Member member);
}
