package woowacourse.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.application.dto.SignUpServiceRequest;
import woowacourse.member.application.dto.UpdateNameServiceRequest;
import woowacourse.member.application.dto.UpdatePasswordServiceRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.password.Password;
import woowacourse.member.domain.password.PlainPassword;
import woowacourse.member.exception.DuplicateEmailException;
import woowacourse.member.exception.InvalidMemberNameException;
import woowacourse.member.exception.InvalidPasswordException;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.member.ui.dto.FindMemberInfoResponse;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public void signUp(SignUpServiceRequest request) {
        checkDuplicateEmail(request.getEmail());
        PlainPassword plainPassword = new PlainPassword(request.getPassword());
        Password password = plainPassword.encrypt();
        Member member = new Member(request.getEmail(), request.getName(), password);
        memberDao.save(member);
    }

    public FindMemberInfoResponse findMemberInfo(long id) {
        Member member = validateExistMember(memberDao.findMemberById(id));
        return new FindMemberInfoResponse(member);
    }

    public void checkDuplicateEmail(String email) {
        if (memberDao.existMemberByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional
    public void updateName(UpdateNameServiceRequest request) {
        Member member = validateExistMember(memberDao.findMemberById(request.getMemberId()));

        if (member.isSameName(request.getName())) {
            throw new InvalidMemberNameException("현재 이름과 같은 이름으로 변경할 수 없습니다.");
        }
        memberDao.updateName(request.getMemberId(), request.getName());
    }

    @Transactional
    public void updatePassword(UpdatePasswordServiceRequest request) {
        Member member = validateExistMember(memberDao.findMemberById(request.getMemberId()));
        validateUpdatePassword(request, member);

        PlainPassword plainPassword = new PlainPassword(request.getNewPassword());
        Password newPassword = plainPassword.encrypt();
        memberDao.updatePassword(request.getMemberId(), newPassword.getValue());
    }

    private Member validateExistMember(Optional<Member> member) {
        return member.orElseThrow(MemberNotFoundException::new);
    }

    private void validateUpdatePassword(UpdatePasswordServiceRequest request, Member member) {
        PlainPassword plainPassword = new PlainPassword(request.getOldPassword());
        Password requestPassword = plainPassword.encrypt();
        if (!member.isSamePassword(requestPassword)) {
            throw new InvalidPasswordException("현재 비밀번호와 일치하지 않습니다.");
        }

        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new InvalidPasswordException("현재 비밀번호와 같은 비밀번호로 변경할 수 없습니다.");
        }
    }

    @Transactional
    public void withdraw(long id) {
        validateExistMember(memberDao.findMemberById(id));
        int deletedRowCount = memberDao.deleteById(id);
        if (deletedRowCount == 0) {
            throw new MemberNotFoundException();
        }
    }
}
