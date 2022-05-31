package woowacourse.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.dto.DuplicateEmailRequest;
import woowacourse.member.dto.MemberInfoResponse;
import woowacourse.member.dto.SignUpRequest;
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
        if (!member.isSamePassword(request.getPassword())) {
            throw new WrongPasswordException("잘못된 비밀번호입니다.");
        }
        return member.getId();
    }

    public MemberInfoResponse findMemberById(long id) {
        Member member = validateExistMember(memberDao.findMemberById(id));
        return new MemberInfoResponse(member);
    }

    public void deleteMemberById(long id) {
        int deletedRowCount = memberDao.deleteById(id);
        if (deletedRowCount == 0) {
            throw new MemberNotFoundException("존재하지 않는 회원입니다.");
        }
    }

    public void checkDuplicateEmail(DuplicateEmailRequest request) {
        if (memberDao.existMemberByEmail(request.getEmail())) {
            throw new DuplicateEmailException("이메일은 중복될 수 없습니다.");
        }
    }

    private Member validateExistMember(Optional<Member> member) {
        return member.orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
    }
}
