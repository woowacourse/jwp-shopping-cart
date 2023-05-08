package cart.service;

import org.springframework.stereotype.Service;

import cart.persistence.dao.MemberDao;

@Service
public class AuthService {

    private final MemberDao memberDao;

    public AuthService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public boolean isNotRegistered(final String email, final String password) {
        return memberDao.findByEmail(email)
            .filter(memberEntity -> memberEntity.getPassword().equals(password))
            .isEmpty();
    }
}
