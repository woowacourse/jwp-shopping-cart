package cart.dao;

import cart.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberDao {

    Long insert(final Member member);

    List<Member> findAll();

    Optional<Member> findByEmailAndPassword(String email, String password);
}
