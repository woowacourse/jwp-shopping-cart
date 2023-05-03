package cart.dao.member;

import cart.domain.member.Member;

import java.util.List;

public interface MemberDao {

    void insert(final Member member);

    Member findByEmailAndPassword(final String email, final String password);

    List<Member> findAll();
}
