package cart.dao;

import cart.entity.Member;

public interface MemberDao {

    void save(Member member);

    boolean isMemberExists(Member member);

    boolean isEmailExists(String email);
}
