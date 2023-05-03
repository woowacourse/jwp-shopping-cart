package cart.repository;

import cart.entity.Member;
import cart.service.dto.MemberInfo;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    List<Member> findAll();

    Optional<Long> findId(MemberInfo memberInfo);
}
