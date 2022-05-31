package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.dto.request.LoginServiceRequest;
import woowacourse.auth.application.dto.request.MemberCreateServiceRequest;
import woowacourse.auth.application.dto.request.MemberUpdateServiceRequest;
import woowacourse.auth.application.dto.response.LoginServiceResponse;
import woowacourse.auth.application.dto.response.MemberServiceResponse;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Email;
import woowacourse.auth.domain.Member;
import woowacourse.auth.domain.Nickname;
import woowacourse.auth.domain.Password;
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
    public void save(MemberCreateServiceRequest memberCreateServiceRequest) {
        validateUniqueEmail(memberCreateServiceRequest);
        Member member = new Member(memberCreateServiceRequest.getEmail(), memberCreateServiceRequest.getPassword(),
                memberCreateServiceRequest
                        .getNickname());
        memberDao.save(member);
    }

    private void validateUniqueEmail(MemberCreateServiceRequest memberCreateServiceRequest) {
        if (existsEmail(memberCreateServiceRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 주소입니다.");
        }
    }

    @Transactional
    public boolean existsEmail(String email) {
        String validatedEmail = new Email(email).getValue();
        return memberDao.existsEmail(validatedEmail);
    }

    @Transactional
    public LoginServiceResponse login(LoginServiceRequest loginServiceRequest) {
        Member member = findByEmail(loginServiceRequest.getEmail(), new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
        validatePassword(member.getPassword(), loginServiceRequest.getPassword());
        String token = tokenManager.createToken(member.getEmail());
        return new LoginServiceResponse(token, member.getNickname());
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
    public boolean checkPassword(String email, String password) {
        Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
        return member.getPassword()
                .equals(password);
    }

    @Transactional
    public MemberServiceResponse findMember(String email) {
        Member member = findByEmail(email, new AuthorizationException("유효하지 않은 토큰입니다."));
        return new MemberServiceResponse(member);
    }

    @Transactional(readOnly = true)
    public void updateMember(String email, MemberUpdateServiceRequest memberUpdateServiceRequest) {
        validateExists(email);
        String nickname = new Nickname(memberUpdateServiceRequest.getNickname())
                .getValue();
        memberDao.updateNicknameByEmail(email, nickname);
    }

    private void validateExists(String email) {
        if (!existsEmail(email)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
    }

    @Transactional(readOnly = true)
    public void updatePassword(String email, String newPassword) {
        validateExists(email);
        String password = new Password(newPassword).getValue();
        memberDao.updatePasswordByEmail(email, password);
    }

    @Transactional(readOnly = true)
    public void delete(String email) {
        validateExists(email);
        memberDao.deleteByEmail(email);
    }
}
