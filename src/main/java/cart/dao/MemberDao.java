package cart.dao;

import java.util.List;
import java.util.Optional;

public interface MemberDao {

    List<MemberEntity> findAll();

    Optional<MemberEntity> findMemberByEmail(final String email);

    Optional<MemberEntity> findMemberById(final int id);

}
