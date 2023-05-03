package cart.dao;

import cart.entity.AuthMember;
import cart.entity.Member;

import java.util.List;

public interface MemberDao {

    List<Member> findAll();

    void save(AuthMember authMember);

    boolean isMemberExists(AuthMember authMember);

    boolean isEmailExists(String email);
}
