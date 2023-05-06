package cart.config.auth;

import cart.domain.member.Member;
import cart.exception.AuthLoginException;
import cart.repository.member.MemberRepository;
import cart.service.request.AuthMember;
import org.springframework.stereotype.Service;

@Service
public class AuthChecker {
    private final MemberRepository memberRepository;

    public AuthChecker(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public AuthMember login(final String email, final String password) {
        final Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthLoginException("이메일 혹은 비밀번호가 틀렸습니다."));

        if (!findMember.getPassword().equals(password)) {
            throw new AuthLoginException("이메일 혹은 비밀번호가 틀렸습니다.");
        }

        return new AuthMember(findMember.getId(), findMember.getEmail());
    }
}
