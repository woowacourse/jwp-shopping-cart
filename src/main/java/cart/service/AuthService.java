package cart.service;

import cart.auth.AuthDto;
import cart.dao.MemberDao;
import cart.domain.Member;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberDao memberDao;

    @Autowired
    public AuthService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public boolean isInvalidAuth(final String email, final String password) {
        final Optional<Member> memberOptional = memberDao.findByEmailAndPassword(email, password);
        return memberOptional.isEmpty();
    }
}
