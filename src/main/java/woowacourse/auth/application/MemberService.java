package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Email;
import woowacourse.auth.domain.Member;
import woowacourse.auth.domain.Nickname;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordUpdateRequest;
import woowacourse.auth.dto.response.MemberResponse;
import woowacourse.auth.exception.AuthorizationException;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public boolean existsEmail(String email) {
        String validatedEmail = new Email(email).getValue();
        return memberDao.existsEmail(validatedEmail);
    }

//    public MemberResponse find(String email) {
//        Member member = memberDao.findByEmail(email)
//                .orElseThrow(() -> new AuthorizationException("회원 정보를 찾지 못했습니다."));
//        return new MemberResponse(member);
//    }

    public MemberResponse find(Long id) {
        Member member = memberDao.findById(id)
                .orElseThrow(() -> new AuthorizationException("회원 정보를 찾지 못했습니다."));
        return new MemberResponse(member);
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequest memberUpdateRequest) {
        validateExists(id);
        String nickname = new Nickname(memberUpdateRequest.getNickname())
                .getValue();
        memberDao.updateNicknameById(id, nickname);
    }

    @Transactional
    public void updatePassword(Long id, PasswordUpdateRequest passwordUpdateRequest) {
        validateExists(id);
        String password = new Password(passwordUpdateRequest.getPassword())
                .getValue();
        memberDao.updatePasswordById(id, password);
    }

    @Transactional
    public void delete(Long id) {
        validateExists(id);
        memberDao.deleteById(id);
    }

    public void validateExists(Long id) {
        if (!memberDao.exists(id)) {
            throw new AuthorizationException("회원 정보를 찾지 못했습니다.");
        }
    }
}
