package cart.auth;

import cart.dao.MemberDao;
import cart.exception.UnAuthorizedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public long validateLogin(String email, String password) throws UnAuthorizedException {
        try {
            return memberDao.findByEmailAndPassword(email, password);
        } catch (EmptyResultDataAccessException e) {
            throw new UnAuthorizedException();
        }
    }
}
