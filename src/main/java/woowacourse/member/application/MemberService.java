package woowacourse.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.InputPassword;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.Name;
import woowacourse.member.domain.Password;
import woowacourse.member.dto.request.*;
import woowacourse.member.dto.response.MemberInfoResponse;
import woowacourse.member.exception.*;

@Service
@Transactional
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public long logIn(LoginRequest request) {
        Member member = findMemberByEmail(request.getEmail());
        Password password = new InputPassword(request.getPassword());

        if (!member.isSamePassword(password)) {
            throw new WrongPasswordException("잘못된 비밀번호입니다.");
        }

        return member.getId();
    }

    public void signUp(SignUpRequest request) {
        validateDuplicateEmail(request.getEmail());

        Member member = new Member(request.getEmail(), request.getName(), new InputPassword(request.getPassword()));
        memberDao.save(member);
    }

    @Transactional(readOnly = true)
    public void checkDuplicateEmail(DuplicateEmailRequest request) {
        validateDuplicateEmail(request.getEmail());
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse findMemberInfoById(long id) {
        Member member = findMemberById(id);
        return new MemberInfoResponse(member);
    }

    public void updateName(long id, UpdateNameRequest request) {
        Member member = findMemberById(id);
        Name name = new Name(request.getName());

        if (member.isSameName(name)) {
            throw new InvalidMemberNameException("현재 이름과 같은 이름으로 변경할 수 없습니다.");
        }

        memberDao.updateNameById(id, request.getName());
    }

    public void updatePassword(long id, UpdatePasswordRequest request) {
        Member member = findMemberById(id);
        validateUpdatePassword(request, member);

        Password newPassword = new InputPassword(request.getNewPassword());
        memberDao.updatePasswordById(id, newPassword.getValue());
    }

    public void deleteMemberById(long id) {
        findMemberById(id);
        memberDao.deleteById(id);
    }

    private Member findMemberByEmail(String email) {
        return memberDao.getByEmail(email);
    }

    private void validateDuplicateEmail(String email) {
        if (memberDao.existsByEmail(email)) {
            throw new DuplicateEmailException("중복되는 이메일이 존재합니다.");
        }
    }

    private Member findMemberById(long id) {
        return memberDao.getById(id);
    }

    private void validateUpdatePassword(UpdatePasswordRequest request, Member member) {
        Password requestPassword = new InputPassword(request.getOldPassword());

        if (!member.isSamePassword(requestPassword)) {
            throw new InvalidPasswordException("현재 비밀번호와 일치하지 않습니다.");
        }

        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new InvalidPasswordException("현재 비밀번호와 같은 비밀번호로 변경할 수 없습니다.");
        }
    }
}
