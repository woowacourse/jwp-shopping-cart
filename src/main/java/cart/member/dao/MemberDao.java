package cart.member.dao;

import cart.member.domain.Member;

import java.util.List;

public interface MemberDao {

    void insert(final Member member);

    Member findByEmailAndPassword(final String email, final String password);

    Member findById(final Long memberId);

    List<Member> findAll();
}
