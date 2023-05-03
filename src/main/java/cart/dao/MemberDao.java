package cart.dao;

import cart.entity.Member;

public interface MemberDao {

    void save(Member member);

    boolean isValidMember(Member member);

    boolean isEmailExists(String email);
}
