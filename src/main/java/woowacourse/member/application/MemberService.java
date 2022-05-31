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

    public Long findIdByEmail(LoginRequest request) {
        Optional<Member> member = memberDao.findMemberByEmail(request.getEmail());
        authenticate(member, request);
        return member.orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다.")).getId();
    }

    private void authenticate(Optional<Member> member, LoginRequest request) {
        if (member.isEmpty()) {
            throw new EmailNotFoundException("존재하지 않는 이메일입니다.");
        }

        if (!member.get().isSamePassword(request.getPassword())) {
            throw new WrongPasswordException("잘못된 비밀번호입니다.");
        }
    }

    public MemberInfoResponse findMemberById(long id) {
        Optional<Member> member = memberDao.findMemberById(id);

        if (member.isEmpty()) {
            throw new MemberNotFoundException("존재하지 않는 회원입니다.");
        }

        return new MemberInfoResponse(member.get());
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
}
