package woowacourse.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Email;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.Nickname;
import woowacourse.member.domain.Password;
import woowacourse.member.dto.request.PasswordRequest;
import woowacourse.member.dto.response.PasswordCheckResponse;
import woowacourse.member.dto.response.UniqueEmailCheckResponse;
import woowacourse.member.exception.AuthorizationException;
import woowacourse.member.support.TokenManager;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.member.dto.request.MemberCreateRequest;
import woowacourse.member.dto.request.MemberUpdateRequest;
import woowacourse.member.dto.response.LoginResponse;
import woowacourse.member.dto.response.MemberResponse;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final TokenManager tokenManager;

    public MemberService(MemberDao memberDao, TokenManager tokenManager) {
        this.memberDao = memberDao;
        this.tokenManager = tokenManager;
    }

    @Transactional
    public void save(MemberCreateRequest memberCreateRequest) {
        validateUniqueEmail(memberCreateRequest);
        Member member = new Member(
                memberCreateRequest.getEmail(),
                memberCreateRequest.getPassword(),
                memberCreateRequest.getNickname()
        );
        memberDao.save(member);
    }

    private void validateUniqueEmail(MemberCreateRequest memberCreateRequest) {
        if (!isUniqueEmail(memberCreateRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 주소입니다.");
        }
    }

    @Transactional(readOnly = true)
    public UniqueEmailCheckResponse checkUniqueEmail(String email) {
        return new UniqueEmailCheckResponse(isUniqueEmail(email));
    }

    private boolean isUniqueEmail(String email) {
        String validatedEmail = new Email(email).getValue();
        return !memberDao.existsEmail(validatedEmail);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        Member member = findByEmailAndPassword(loginRequest);
        String token = tokenManager.createToken(member.getId());
        return new LoginResponse(token, member.getNickname());
    }

    private Member findByEmailAndPassword(LoginRequest loginRequest) {
        return memberDao.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
    }

    @Transactional(readOnly = true)
    public PasswordCheckResponse checkPassword(long memberId, PasswordRequest passwordRequest) {
        boolean isRightPassword = memberDao.checkPassword(memberId, passwordRequest.getPassword());
        return new PasswordCheckResponse(isRightPassword);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMember(long memberId) {
        validateId(memberId);
        Member member = memberDao.findById(memberId);
        return new MemberResponse(member);
    }

    @Transactional
    public void updateMember(long memberId, MemberUpdateRequest memberUpdateRequest) {
        validateId(memberId);
        String nickname = new Nickname(memberUpdateRequest.getNickname())
                .getValue();
        memberDao.updateNicknameById(memberId, nickname);
    }

    @Transactional
    public void updatePassword(long memberId, PasswordRequest passwordRequest) {
        validateId(memberId);
        String password = new Password(passwordRequest.getPassword()).getValue();
        memberDao.updatePasswordById(memberId, password);
    }

    @Transactional
    public void deleteMember(long memberId) {
        validateId(memberId);
        memberDao.deleteById(memberId);
    }

    private void validateId(long memberId) {
        if (!memberDao.checkIdExistence(memberId)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
    }
}
