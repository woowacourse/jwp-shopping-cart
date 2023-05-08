package cart.persistence.dao;

import java.util.List;
import java.util.Optional;

import cart.persistence.entity.Member;

public interface MemberDao {

    long save(Member member);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();
}
