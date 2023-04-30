package cart.dao;

import cart.controller.dto.MemberRequest;
import cart.dao.entity.MemberEntity;
import java.util.List;

public interface MemberDao {
    long add(MemberRequest request);

    List<MemberEntity> findAll();
}
