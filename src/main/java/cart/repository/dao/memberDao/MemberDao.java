package cart.repository.dao.memberDao;

import cart.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    Optional<Long> save(final Member member);

    Optional<Member> findById(final Long id);

    Optional<Member> findByEmail(final String email);

    List<Member> findAll();
}
