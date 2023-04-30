package cart.dao;

import cart.controller.dto.MemberRequest;
import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberDao {
    long add(MemberRequest request);

    List<MemberEntity> findAll();

    long findIdByMember(Member member);
}
