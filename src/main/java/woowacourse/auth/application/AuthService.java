package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Email;
import woowacourse.auth.domain.Member;
import woowacourse.auth.domain.Nickname;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.request.PasswordUpdateRequest;
import woowacourse.auth.dto.response.CheckResponse;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.MemberResponse;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.auth.support.TokenManager;

@Service
public class AuthService {

    private final MemberDao memberDao;
    private final TokenManager tokenManager;

    public AuthService(MemberDao memberDao, TokenManager tokenManager) {
        this.memberDao = memberDao;
        this.tokenManager = tokenManager;
    }

    @Transactional(readOnly = true)
    public void save(MemberCreateRequest memberCreateRequest) {
        validateUniqueEmail(memberCreateRequest);
        Member member = new Member(memberCreateRequest.getEmail(), memberCreateRequest.getPassword(),
                memberCreateRequest
                        .getNickname());
        memberDao.save(member);
    }

    private void validateUniqueEmail(MemberCreateRequest memberCreateRequest) {
        if (existsEmail(memberCreateRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 주소입니다.");
        }
    }

    @Transactional
    public boolean existsEmail(String email) {
        String validatedEmail = new Email(email).getValue();
        return memberDao.existsEmail(validatedEmail);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        Member member = findByEmail(loginRequest.getEmail(), new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
        validatePassword(member.getPassword(), loginRequest.getPassword());
        String token = tokenManager.createToken(member.getEmail());
        return new LoginResponse(token, member.getNickname());
    }

    private Member findByEmail(String email, RuntimeException exception) {
        return memberDao.findByEmail(email)
                .orElseThrow(() -> exception);
    }

    private void validatePassword(String input, String saved) {
        if (!input.equals(saved)) {
            throw new IllegalArgumentException("이메일과 비밀번호를 확인해주세요.");
        }
    }

    @Transactional
    public CheckResponse checkPassword(String email, PasswordCheckRequest passwordCheckRequest) {
        Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
        boolean result = member.getPassword()
                .equals(passwordCheckRequest.getPassword());
        return new CheckResponse(result);
    }

    @Transactional
    public MemberResponse findMember(String email) {
        Member member = findByEmail(email, new AuthorizationException("유효하지 않은 토큰입니다."));
        return new MemberResponse(member);
    }

    @Transactional(readOnly = true)
    public void updateMember(String email, MemberUpdateRequest memberUpdateRequest) {
        validateExists(email);
        String nickname = new Nickname(memberUpdateRequest.getNickname())
                .getValue();
        memberDao.updateNicknameByEmail(email, nickname);
    }

    private void validateExists(String email) {
        if (!existsEmail(email)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
    }

    @Transactional(readOnly = true)
    public void updatePassword(String email, PasswordUpdateRequest passwordUpdateRequest) {
        validateExists(email);
        String password = new Password(passwordUpdateRequest.getPassword())
                .getValue();
        memberDao.updatePasswordByEmail(email, password);
    }

    @Transactional(readOnly = true)
    public void delete(String email) {
        validateExists(email);
        memberDao.deleteByEmail(email);
    }
}
