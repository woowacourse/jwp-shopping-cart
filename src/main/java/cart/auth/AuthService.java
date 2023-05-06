package cart.auth;

import cart.entity.MemberEntity;
import cart.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public User findMemberByEmail(String email) {
        Optional<MemberEntity> nullableEntity = memberRepository.findByEmail(email);
        if (nullableEntity.isEmpty()) {
            throw new IllegalStateException("회원을 찾을 수 없습니다.");
        }
        MemberEntity entity = nullableEntity.get();
        return new User(entity.getId(), entity.getEmail());
    }
}
