package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Email;
import woowacourse.auth.domain.Member;
import woowacourse.auth.domain.Nickname;
import woowacourse.auth.domain.Password;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.auth.support.TokenManager;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.MemberResponse;

@Service
public class AuthService {

    private final MemberDao memberDao;
    private final TokenManager tokenManager;

    public AuthService(MemberDao memberDao, TokenManager tokenManager) {
        this.memberDao = memberDao;
        this.tokenManager = tokenManager;
    }

    @Transactional
    public void save(MemberCreateRequest memberCreateRequest) {
        validateUniqueEmail(memberCreateRequest);
        Member member = new Member(memberCreateRequest.getEmail(), memberCreateRequest.getPassword(),
                memberCreateRequest.getNickname());
        memberDao.save(member);
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
        Member member = findByEmailAndPassword(loginRequest);
        String token = tokenManager.createToken(member.getId());
        return new LoginResponse(token, member.getNickname());
    }

    private Member findByEmailAndPassword(LoginRequest loginRequest) {
        return memberDao.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
    }

    @Transactional(readOnly = true)
    public boolean checkPassword(long memberId, String password) {
        return memberDao.checkIdAndPassword(memberId, password);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMember(long memberId) {
        Member member = memberDao.findById(memberId)
                .orElseThrow(() -> new AuthorizationException("유효하지 않은 토큰입니다."));
        return new MemberResponse(member);
    }

    @Transactional
    public void updateMember(long memberId, MemberUpdateRequest memberUpdateRequest) {
        validateId(memberId);
        String nickname = new Nickname(memberUpdateRequest.getNickname())
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
