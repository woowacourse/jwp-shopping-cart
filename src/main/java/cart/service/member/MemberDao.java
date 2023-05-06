package cart.service.member;

import cart.service.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    Member save(Member member);

    List<Member> findAll();

    Optional<Member> findByEmail(String email);
}
