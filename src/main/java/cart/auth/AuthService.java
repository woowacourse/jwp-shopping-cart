package cart.auth;

import cart.dao.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public long validateLogin(String email, String password) {
        return memberDao.findByEmailAndPassword(email, password);
    }
}
