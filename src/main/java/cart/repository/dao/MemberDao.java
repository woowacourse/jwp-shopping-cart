package cart.repository.dao;

import cart.repository.entity.MemberEntity;
import java.util.List;

public interface MemberDao {

    List<MemberEntity> findAll();

    MemberEntity findByEmailAndPassword(final String email, final String password);
}
