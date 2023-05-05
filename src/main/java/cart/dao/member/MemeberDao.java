package cart.dao.member;

import cart.domain.Member;
import cart.dto.MemeberDto;

import java.util.List;
import java.util.Optional;

public interface MemeberDao {

    Optional<Member> findByEmail(String email);

    void save(MemeberDto memeberDto);

    List<Member> findAll();

}
