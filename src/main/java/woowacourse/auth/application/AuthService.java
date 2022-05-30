package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Member;
import woowacourse.auth.domain.Nickname;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.AuthorizedMember;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.auth.dto.MemberCreateRequest;
import woowacourse.auth.dto.NicknameUpdateRequest;
import woowacourse.auth.dto.PasswordCheckRequest;
import woowacourse.auth.dto.PasswordUpdateRequest;
import woowacourse.auth.support.JwtTokenProvider;

@Service
public class AuthService {

    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

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

    public boolean checkPassword(String email, PasswordCheckRequest passwordCheckRequest) {
        Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));
        return member.getPassword()
                .equals(passwordCheckRequest.getPassword());
    }

    private Member findMemberByToken(String token) {
        String email = jwtTokenProvider.getPayload(token);
        return memberDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
    }

    public AuthorizedMember findAuthorizedMemberByToken(String token) {
        Member member = findMemberByToken(token);
        return new AuthorizedMember(member);
    }

    public void updateNickname(String email, NicknameUpdateRequest nicknameUpdateRequest) {
        validateExists(email);
        String nickname = new Nickname(nicknameUpdateRequest.getNickname())
                .getValue();
        memberDao.updateNicknameByEmail(email, nickname);
    }

    private void validateExists(String email) {
        if (!existsEmail(email)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }

    public void updatePassword(String email, PasswordUpdateRequest passwordUpdateRequest) {
        validateExists(email);
        String password = new Password(passwordUpdateRequest.getPassword())
                .getValue();
        memberDao.updatePasswordByEmail(email, password);
    }

    public void delete(String email) {
        validateExists(email);
        memberDao.deleteByEmail(email);
    }
}
