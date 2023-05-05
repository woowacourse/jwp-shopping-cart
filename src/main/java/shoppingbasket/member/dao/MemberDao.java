package shoppingbasket.member.dao;

import shoppingbasket.member.entity.MemberEntity;
import java.util.List;
import java.util.Optional;

public interface MemberDao {

    List<MemberEntity> selectAll();

    Optional<MemberEntity> findByEmail(String memberEmail);

    MemberEntity insert(MemberEntity member);
}
