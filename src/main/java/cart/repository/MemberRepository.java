package cart.repository;

import cart.entity.Member;
import java.util.List;

public interface MemberRepository {

    List<Member> findAll();
}
