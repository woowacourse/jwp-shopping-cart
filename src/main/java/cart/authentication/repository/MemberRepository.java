package cart.authentication.repository;

import cart.authentication.entity.Member;
import cart.authentication.exception.MemberPersistanceFailedException;

import java.util.List;

public interface MemberRepository {
    Member save(Member member) throws MemberPersistanceFailedException;

    Member findByEmail(String email) throws MemberPersistanceFailedException;

    List<Member> findAll();
}
