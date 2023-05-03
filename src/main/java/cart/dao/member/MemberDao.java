package cart.dao.member;

import cart.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    MemberEntity save(MemberEntity item);

    List<MemberEntity> findAll();

    Optional<MemberEntity> findByEmail(String email);

    void update(MemberEntity item);

    void delete(String email);
}
