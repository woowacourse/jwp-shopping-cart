package cart.dao;

import cart.entity.MemberEntity;
import cart.entity.ProductEntity;

import java.util.List;

public interface MemberDao {

    List<MemberEntity> selectAll();
}
