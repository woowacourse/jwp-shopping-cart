package cart.entity.member;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    Member save(Member member);

    List<Member> findAll();

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);
}
