package cart.dao;

import cart.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    Optional<MemberEntity> save(MemberEntity member);

    MemberEntity update(MemberEntity entity);

    Optional<MemberEntity> findById(Long id);

    List<MemberEntity> findAll();

    void deleteById(Long id);

    Optional<MemberEntity> findByEmail(String email);
}
