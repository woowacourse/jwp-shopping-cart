package cart.dao;

import cart.entity.MemberEntity;

import java.util.List;

public interface MemberDao {

    MemberEntity save(MemberEntity item);

    List<MemberEntity> findAll();

    MemberEntity findByEmail(String email);

    void update(MemberEntity item);

    void delete(String email);
}
