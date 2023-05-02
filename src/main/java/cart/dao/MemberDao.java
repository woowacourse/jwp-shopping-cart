package cart.dao;

import cart.entity.Member;
import java.util.List;

public interface MemberDao {

  List<Member> findAll();
}
