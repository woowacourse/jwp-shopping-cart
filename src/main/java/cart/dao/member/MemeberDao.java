package cart.dao.member;

import cart.domain.Member;
import cart.dto.MemeberDto;

import java.util.List;

public interface MemeberDao {

    Member findByEmail(String email);

    void save(MemeberDto memeberDto);

    List<Member> findAll();

}
