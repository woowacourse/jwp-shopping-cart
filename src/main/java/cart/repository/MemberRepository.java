package cart.repository;

import cart.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    MemberEntity save(MemberEntity memberEntity);

    Optional<MemberEntity> findById(Long id);

    List<MemberEntity> findAll();

    void update(MemberEntity memberEntity);

    void deleteById(Long id);
}
