package cart.dao.member;

import cart.domain.member.Member;
import java.util.List;

public interface MemberDao {

    List<Member> findAll();
}
