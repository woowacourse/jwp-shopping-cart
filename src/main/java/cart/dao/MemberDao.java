package cart.dao;

import cart.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberDao {

    List<Member> findAll();

    Optional<Member> findByEmailAndPassword(String email, String password);
}
