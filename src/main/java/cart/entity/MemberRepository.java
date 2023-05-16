package cart.entity;

import java.util.List;

public interface MemberRepository {
    Long save(Member member);

    Member findByEmailAndPassword(String email, String password);

    List<Member> findAll();

    void updateById(Member member, Long id);

    void deleteById(Long id);
}
