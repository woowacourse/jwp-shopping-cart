package cart.auth;

import cart.dao.member.MemberDao;
import cart.domain.member.Member;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final MemberDao memberDao;

    public AuthenticationService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Member login(final String email, final String password) {

        return memberDao.findByEmailAndPassword(email, password);
    }
}
