package cart.domain;

import java.util.List;

public interface MemberRepository {

    Member save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    Member findByEmail(String email);
}
