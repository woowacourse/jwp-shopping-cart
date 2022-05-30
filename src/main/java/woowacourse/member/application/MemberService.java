package woowacourse.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LonginRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.Password;
import woowacourse.member.dto.SignUpRequest;
import woowacourse.member.exception.InvalidMemberEmailException;
import woowacourse.member.exception.LonginWrongEmailException;
import woowacourse.member.exception.LonginWrongPasswordException;

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

        Member member = new Member(request.getEmail(), request.getName(), request.getPassword());
        memberDao.save(member);
    }

    public void verifyValidMember(LonginRequest request) {
        Optional<String> savedPassword = memberDao.findPasswordByEmail(request.getEmail());

        if (savedPassword.isEmpty()) {
            throw new LonginWrongEmailException("존재하지 않는 이메일입니다.");
        }

        Password password = new Password(request.getPassword());
        if (!password.isSameAs(savedPassword.get())) {
            throw new LonginWrongPasswordException("잘못된 비밀번호입니다.");
        }
    }
}
