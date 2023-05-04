package cart.domain;

import cart.dto.LoginDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    List<Member> findAll();

    boolean contains(Member member);

    Optional<Member> findByEmailAndPassword(LoginDto loginDto);
}
