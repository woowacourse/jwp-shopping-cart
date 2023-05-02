package cart.dao;

import cart.entity.MemberEntity;
import java.util.List;
import java.util.Optional;

public interface MemberDao {

  List<MemberEntity> findAll();

  Optional<MemberEntity> findByMemberEntity(MemberEntity memberEntity);
}
