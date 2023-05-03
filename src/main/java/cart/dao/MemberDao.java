package cart.dao;

import cart.entity.MemberEntity;

import java.util.List;

public interface MemberDao {

    List<MemberEntity> selectAllMembers();

    int findMemberId(String email, String password);
}
