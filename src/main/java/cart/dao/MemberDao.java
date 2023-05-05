package cart.dao;

import cart.domain.entity.Member;

import java.util.List;

public interface MemberDao {

    List<Member> selectAll();

    long insert(final Member member);

    Member selectByEmailAndPassword(final Member member);
}
