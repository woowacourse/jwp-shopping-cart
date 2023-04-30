package cart.repository;

import cart.entity.Member;
import cart.repository.exception.MemberPersistanceFailedException;

import java.util.List;

public interface MemberRepository {
    Member save(Member member) throws MemberPersistanceFailedException;

    Member findByEmail(String email) throws MemberPersistanceFailedException;

    List<Member> findAll();
}
