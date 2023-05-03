package cart.domain.persistence.dao;

import java.util.List;
import java.util.Optional;

import cart.domain.persistence.entity.MemberEntity;

public interface MemberDao {

    Long save(MemberEntity memberEntity);

    Optional<MemberEntity> findByEmail(String email);

    List<MemberEntity> findAll();
}
