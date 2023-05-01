package cart.domain.settings.persistence.dao;

import java.util.List;

import cart.domain.settings.persistence.entity.MemberEntity;

public interface MemberDao {

    Long save(MemberEntity memberEntity);

    MemberEntity findByEmail(String email);

    List<MemberEntity> findAll();
}
