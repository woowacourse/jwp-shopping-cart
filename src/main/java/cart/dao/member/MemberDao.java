package cart.dao.member;

import cart.entity.member.Member;
import java.util.List;

public interface MemberDao {

    public List<Member> findAll();
}
