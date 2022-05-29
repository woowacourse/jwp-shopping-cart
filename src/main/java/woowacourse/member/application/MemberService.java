package woowacourse.member.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.exception.DuplicateMemberEmailException;
import woowacourse.member.exception.NoMemberException;
import woowacourse.member.exception.WrongPasswordException;
import woowacourse.member.infrastructure.PasswordEncoder;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Long save(final MemberRegisterRequest memberRegisterRequest) {
        validateDuplicateEmail(memberRegisterRequest.getEmail());
        Member member = memberRegisterRequest.toEntity();
        member.encodePassword(passwordEncoder);
        return memberDao.save(member);
    }

    private void validateDuplicateEmail(final String email) {
        if(memberDao.isEmailExist(email)) {
            throw new DuplicateMemberEmailException();
        }
    }

    public Member login(final TokenRequest tokenRequest) {
        Member member = memberDao.findByEmail(tokenRequest.getEmail())
                .orElseThrow(NoMemberException::new);

        String encode = passwordEncoder.encode(tokenRequest.getPassword());
        if (!member.authenticate(encode)) {
            throw new WrongPasswordException();
        }
        return member;
    }
}
