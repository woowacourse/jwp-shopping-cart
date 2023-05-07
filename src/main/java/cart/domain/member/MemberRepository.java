package cart.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Long save(Member memberToSave);

    List<Member> findAll();

    Optional<Member> findByEmail(String email);
}
