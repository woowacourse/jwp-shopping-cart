package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.PasswordCheckResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.MemberDao;
import woowacourse.shoppingcart.domain.Member;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberDao.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
        validatePassword(member.getPassword(), loginRequest.getPassword());
        String token = jwtTokenProvider.createToken(member.getId().toString());
        return new LoginResponse(token, member.getNickname());
    }

    private void validatePassword(String input, String saved) {
        if (!input.equals(saved)) {
            throw new IllegalArgumentException("이메일과 비밀번호를 확인해주세요.");
        }
    }

    public PasswordCheckResponse checkPassword(Long memberId, PasswordCheckRequest passwordCheckRequest) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
        boolean result = member.matchPassword(passwordCheckRequest.getPassword());
        return new PasswordCheckResponse(result);
    }
}
