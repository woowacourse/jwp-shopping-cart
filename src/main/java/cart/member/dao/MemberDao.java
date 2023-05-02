package cart.member.dao;

import cart.member.domain.Member;

import java.util.List;

public interface MemberDao {
    Long save(final Member member);
    
    List<Member> findAll();
    
    Member findByEmailAndPassword(final String email, final String password);
    
    void deleteAll();
}
