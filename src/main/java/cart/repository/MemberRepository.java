package cart.repository;

import static java.util.stream.Collectors.toList;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.entity.MemberEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@AllArgsConstructor
public class MemberRepository {

    private final MemberDao memberDao;

    public List<Member> findAll() {
        final List<MemberEntity> result = memberDao.findAll();

        return result.stream()
                .map(memberEntity -> Member.builder()
                        .age(memberEntity.getAge())
                        .address(memberEntity.getAddress())
                        .email(memberEntity.getEmail())
                        .password(memberEntity.getPassword())
                        .name(memberEntity.getName()).build()
                ).collect(toList());
    }
}
