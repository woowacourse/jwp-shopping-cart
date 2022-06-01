package woowacourse.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.Password;
import woowacourse.member.dto.*;
import woowacourse.member.exception.*;

import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void signUp(SignUpRequest request) {
        if (memberDao.existMemberByEmail(request.getEmail())) {
            throw new InvalidMemberEmailException("중복되는 이메일이 존재합니다.");
        }

        Member member = Member.withEncrypt(request.getEmail(), request.getName(), request.getPassword());
        memberDao.save(member);
    }

    public Long authenticate(LoginRequest request) {
        Member member = validateExistMember(memberDao.findMemberByEmail(request.getEmail()));
        Password requestPassword = Password.withEncrypt(request.getPassword());
        if (!member.isSamePassword(requestPassword)) {
            throw new WrongPasswordException("잘못된 비밀번호입니다.");
        }
        return member.getId();
    }

    public MemberInfoResponse findMemberInfo(long id) {
        Member member = validateExistMember(memberDao.findMemberById(id));
        return new MemberInfoResponse(member);
    }

    public void checkDuplicateEmail(DuplicateEmailRequest request) {
        if (memberDao.existMemberByEmail(request.getEmail())) {
            throw new DuplicateEmailException("이메일은 중복될 수 없습니다.");
        }
    }

    public void updateName(long id, UpdateNameRequest request) {
        Member member = validateExistMember(memberDao.findMemberById(id));

        if (member.isSameName(request.getName())) {
            throw new InvalidMemberNameException("현재 이름과 같은 이름으로 변경할 수 없습니다.");
        }
        memberDao.updateName(id, request.getName());
    }

    public void updatePassword(long id, UpdatePasswordRequest request) {
        Member member = validateExistMember(memberDao.findMemberById(id));
        validateUpdatePassword(request, member);

        Password newPassword = Password.withEncrypt(request.getNewPassword());
        memberDao.updatePassword(id, newPassword.getValue());
    }

    private Member validateExistMember(Optional<Member> member) {
        return member.orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
    }

    private void validateUpdatePassword(UpdatePasswordRequest request, Member member) {
        Password requestPassword = Password.withEncrypt(request.getOldPassword());
        if (!member.isSamePassword(requestPassword)) {
            throw new InvalidPasswordException("현재 비밀번호와 일치하지 않습니다.");
        }

        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new InvalidPasswordException("현재 비밀번호와 같은 비밀번호로 변경할 수 없습니다.");
        }
    }

    public void withdrawal(long id, WithdrawalRequest request) {
        Member member = validateExistMember(memberDao.findMemberById(id));
        Password requestPassword = Password.withEncrypt(request.getPassword());
        if (!member.isSamePassword(requestPassword)) {
            throw new InvalidPasswordException("현재 비밀번호와 일치하지 않습니다.");
        }

        int deletedRowCount = memberDao.deleteById(id);
        if (deletedRowCount == 0) {
            throw new MemberNotFoundException("존재하지 않는 회원입니다.");
        }
    }
}
