package cart.dao;

import cart.entity.Member;
import java.util.Optional;

public interface MemberDao {

    Member save(Member member);

    Optional<Member> findById(Long id);
}
