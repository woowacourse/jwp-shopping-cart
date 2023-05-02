package cart.entity.member;

import java.util.List;

public interface MemberDao {

    Member save(Member member);

    List<Member> findAll();

    Member findById(long id);

    Member update(Member member);
}
