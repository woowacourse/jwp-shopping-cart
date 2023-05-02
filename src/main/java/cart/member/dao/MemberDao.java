package cart.member.dao;

import cart.member.entity.MemberEntity;
import java.util.List;
import java.util.Optional;

public interface MemberDao {

    List<MemberEntity> selectAll();

    Optional<MemberEntity> findByEmail(String memberEmail);
}
