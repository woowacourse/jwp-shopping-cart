package cart.dao;

import cart.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    List<Member> selectAll();

    long insert(final Member member);

    Optional<Member> selectByEmail(final String email);
}
