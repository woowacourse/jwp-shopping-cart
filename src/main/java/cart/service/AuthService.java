package cart.service;

import cart.dto.cart.UserDto;
import cart.entity.MemberEntity;
import cart.exception.AuthorizationException;
import cart.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public UserDto findMemberByEmail(String email) {
        Optional<MemberEntity> nullableEntity = memberRepository.findByEmail(email);
        if (nullableEntity.isPresent()) {
            return UserDto.fromMemberEntity(nullableEntity.get());
        }

        throw new AuthorizationException("회원을 찾을 수 없습니다.");
    }
}
