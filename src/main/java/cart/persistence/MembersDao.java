package cart.persistence;

import cart.domain.member.Member;

import java.util.List;

public interface MembersDao {

    List<Member> findAll();

    boolean isMemberCertified(String email, String password);

    Long findIdByEmail(String email);
}
