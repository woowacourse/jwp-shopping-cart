package cart.dao;

import cart.entity.MemberEntity;

import java.util.List;

public interface MemberDao {

    List<MemberEntity> selectAll();

    long insert(final MemberEntity memberEntity);

    MemberEntity selectByEmailAndPassword(final MemberEntity memberEntity);
}
