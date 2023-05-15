package cart.dao;

import cart.domain.member.Email;
import cart.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberDao {

    List<Member> findAll();

    Optional<Member> findByEmail(final Email email);
}
