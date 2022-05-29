package woowacourse.member.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.member.dao.MemberDao;
import woowacourse.member.domain.Member;
import woowacourse.member.dto.MemberRegisterRequest;
import woowacourse.member.exception.DuplicateMemberEmailException;
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

    public boolean isLogin(final TokenRequest tokenRequest) {
        Member member = memberDao.findByEmail(tokenRequest.getEmail());

        String encode = passwordEncoder.encode(tokenRequest.getPassword());
        return member.authenticate(encode);
    }
}
