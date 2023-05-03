package cart.product.dao;

import cart.product.domain.Member;
import java.util.List;

public interface MemberDao {

    List<Member> findAll();
}
