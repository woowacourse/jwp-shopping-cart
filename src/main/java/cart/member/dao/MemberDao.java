package cart.member.dao;

import cart.member.entity.MemberEntity;
import java.util.List;

public interface MemberDao {

    List<MemberEntity> selectAll();
}
