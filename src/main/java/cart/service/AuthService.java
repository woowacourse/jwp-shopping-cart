package cart.service;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import cart.exception.auth.UnauthenticatedException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberDao memberDao;

    public AuthService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public Member login(String email, String password) {
        Optional<Member> member = memberDao.findByEmailAndPassword(email, password);
        if (member.isEmpty()) {
            throw new UnauthenticatedException();
        }
        return member.get();
    }
}
