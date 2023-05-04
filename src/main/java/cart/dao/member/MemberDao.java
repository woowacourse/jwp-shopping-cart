package cart.dao.member;

import cart.entity.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberDao {

    public List<Member> findAll();

    public Optional<Member> findByEmail(String email);
}
