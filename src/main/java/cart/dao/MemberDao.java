package cart.dao;

import cart.entity.MemberEntity;
import java.util.List;

public interface MemberDao {

    List<MemberEntity> selectAll();
}
