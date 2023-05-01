package cart.authentication.repository;

import cart.authentication.entity.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);

    Member findByEmail(String email);

    List<Member> findAll();
}
