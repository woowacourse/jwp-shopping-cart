package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Member;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.dto.MemberRequest;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private MemberDao memberDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void save(MemberRequest memberRequest) {
        validateUniqueEmail(memberRequest);
        Member member = new Member(memberRequest.getEmail(), memberRequest.getPassword(), memberRequest.getNickname());
        memberDao.save(member);
    }

    private void validateUniqueEmail(MemberRequest memberRequest) {
        if (existsEmail(memberRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 주소입니다.");
        }
    }

    public boolean existsEmail(String email) {
        return memberDao.existsEmail(email);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = findByEmail(loginRequest.getEmail());
        validatePassword(member.getPassword(), loginRequest.getPassword());
        String token = jwtTokenProvider.createToken(member.getEmail());
        return new LoginResponse(token, member.getNickname());
    }

    private Member findByEmail(String email) {
        return memberDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
    }

    private void validatePassword(String input, String saved) {
        if (!input.equals(saved)) {
            throw new IllegalArgumentException("이메일과 비밀번호를 확인해주세요.");
        }
    }
}
