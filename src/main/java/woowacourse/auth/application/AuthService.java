package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.password.NewPassword;
import woowacourse.member.domain.password.Password;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.member.exception.WrongPasswordException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public LoginResponse createToken(Long id) {
        return new LoginResponse(jwtTokenProvider.createToken(String.valueOf(id)));
    }

    public Long authenticate(LoginRequest request) {
        Member member = validateExistMember(memberDao.findMemberByEmail(request.getEmail()));
        Password requestPassword = new NewPassword(request.getPassword());
        if (!member.isSamePassword(requestPassword)) {
            throw new WrongPasswordException("잘못된 비밀번호입니다.");
        }
        return member.getId();
    }

    private Member validateExistMember(Optional<Member> member) {
        return member.orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
    }

    public Long extractIdFromRequest(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        return Long.parseLong(jwtTokenProvider.getPayload(token));
    }

    public void validateToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);

        if (token == null) {
            throw new InvalidTokenException("토큰이 없습니다.");
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        }
    }
}
