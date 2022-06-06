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

    @Transactional
    public void save(MemberCreateServiceRequest memberCreateServiceRequest) {
        validateUniqueEmail(memberCreateServiceRequest);
        Member member = new Member(memberCreateServiceRequest.getEmail(), memberCreateServiceRequest.getPassword(),
                memberCreateServiceRequest.getNickname());
        memberDao.save(member);
    }

    private void validateUniqueEmail(MemberCreateServiceRequest memberCreateServiceRequest) {
        if (existsEmail(memberCreateServiceRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 주소입니다.");
        }
    }

    @Transactional(readOnly = true)
    public boolean existsEmail(String email) {
        String validatedEmail = new Email(email).getValue();
        return memberDao.existsEmail(validatedEmail);
    }

    @Transactional(readOnly = true)
    public LoginServiceResponse login(LoginServiceRequest loginServiceRequest) {
        Member member = findByEmailAndPassword(loginServiceRequest);
        String token = tokenManager.createToken(member.getId());
        return new LoginServiceResponse(token, member.getNickname());
    }

    private Member findByEmailAndPassword(LoginServiceRequest loginServiceRequest) {
        return memberDao.findByEmailAndPassword(loginServiceRequest.getEmail(), loginServiceRequest.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
    }

    @Transactional(readOnly = true)
    public boolean checkPassword(long memberId, String password) {
        return memberDao.checkIdAndPassword(memberId, password);
    }

    @Transactional(readOnly = true)
    public MemberServiceResponse findMember(long memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new AuthorizationException("유효하지 않은 토큰입니다."));
        return new MemberServiceResponse(member);
    }

    @Transactional
    public void updateMember(long memberId, MemberUpdateServiceRequest memberUpdateServiceRequest) {
        validateId(memberId);
        String nickname = new Nickname(memberUpdateServiceRequest.getNickname())
                .getValue();
        memberDao.updateNicknameByEmail(memberId, nickname);
    }

    @Transactional
    public void updatePassword(long memberId, String newPassword) {
        validateId(memberId);
        String password = new Password(newPassword).getValue();
        memberDao.updatePasswordByEmail(memberId, password);
    }

    @Transactional
    public void delete(long memberId) {
        validateId(memberId);
        memberDao.deleteById(memberId);
    }

    private void validateId(long memberId) {
        if (!memberDao.existsId(memberId)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
    }
}
