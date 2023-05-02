package cart.business;

import cart.business.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {

    List<Member> findAll();
}
