package cart.business;

import cart.business.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {

    List<Member> findAll();

    Optional<Integer> findAndReturnId(Member member);
}
