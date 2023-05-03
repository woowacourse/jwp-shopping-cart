package cart.dao;

import cart.entity.Member;

public interface MemberDao {

    boolean isMemberExists(Member member);

    boolean isValidMember(Member member);

    boolean isEmailExists(String email);
}
