package cart.dao.member;

import cart.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemeberDao {

    Optional<Member> findByEmail(String email);

    void save(String email, String password);

    List<Member> findAll();

}
