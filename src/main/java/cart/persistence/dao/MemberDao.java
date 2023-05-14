package cart.persistence.dao;

import cart.persistence.entity.MemberEntity;
import java.util.List;
import java.util.Optional;

public interface MemberDao {

    long save(MemberEntity memberEntity);

    List<MemberEntity> findAll();

    Optional<MemberEntity> findByEmail(String email);
}
