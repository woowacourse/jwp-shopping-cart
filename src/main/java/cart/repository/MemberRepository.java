package cart.repository;

import static java.util.stream.Collectors.toList;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.AuthInfo;
import cart.entity.MemberEntity;
import cart.exception.WrongAuthException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MemberRepository {

    private final MemberDao memberDao;

    public List<Member> findAll() {
        final List<MemberEntity> result = memberDao.findAll();

        return result.stream()
                .map(this::toDomain)
                .collect(toList());
    }

    public Member findByAuthInfo(final AuthInfo authInfo) {
        final Optional<MemberEntity> result = memberDao.findByAuthInfo(authInfo);

        if (result.isPresent()) {
            return toDomain(result.get());
        }
        throw new WrongAuthException();
    }

    private Member toDomain(final MemberEntity memberEntity) {
        return Member.builder()
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .age(memberEntity.getAge())
                .address(memberEntity.getAddress())
                .name(memberEntity.getName()).build();
    }
}
