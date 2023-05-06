package cart.service;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.exception.custom.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public Member findAuthInfo(String email, String password) {
        Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("해당하는 email의 회원이 존재하지 않습니다."));
        if (member.isWrongPassword(password)) {
            throw new UnauthorizedException("패스워드가 일치하지 않습니다.");
        }
        return member;
    }
}
