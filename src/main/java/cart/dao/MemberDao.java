package cart.dao;

import cart.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberDao {
    Optional<Long> selectMemberId(final MemberEntity memberEntity);

    List<MemberEntity> findAll();
}
