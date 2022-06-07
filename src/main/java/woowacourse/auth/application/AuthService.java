package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Email;
import woowacourse.auth.domain.Member;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.PasswordCheckResponse;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public Long save(MemberCreateRequest memberCreateRequest) {
        validateUniqueEmail(memberCreateRequest);
        Member member = new Member(memberCreateRequest.getEmail(), memberCreateRequest.getPassword(),
                memberCreateRequest.getNickname());
        return memberDao.save(member);
    }

    private void validateUniqueEmail(MemberCreateRequest memberCreateRequest) {
        if (existsEmail(memberCreateRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 주소입니다.");
        }
    }

    @Transactional(readOnly = true)
    public boolean existsEmail(String email) {
        String validatedEmail = new Email(email).getValue();
        return memberDao.existsEmail(validatedEmail);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public PasswordCheckResponse checkPassword(Long memberId, PasswordCheckRequest passwordCheckRequest) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
        boolean result = member.getPassword()
                .equals(passwordCheckRequest.getPassword());
        return new PasswordCheckResponse(result);
    }
}
