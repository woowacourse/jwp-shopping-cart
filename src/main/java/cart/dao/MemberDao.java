package cart.dao;

import cart.controller.dto.MemberRequest;
import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao {

    long add(Member member);

    List<MemberEntity> findAll();

    Optional<Long> findIdByMember(Member member);
}
