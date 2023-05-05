package cart.service;

import cart.dao.member.MemberDao;
import cart.dao.member.MemberEntity;
import cart.global.exception.auth.InvalidAuthorizationException;
import cart.global.infrastructure.Credential;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberDao memberDao;

    public AuthService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberEntity getMemberEntity(final Credential credential) {
        return memberDao.findByEmailAndPassword(
                credential.getEmail(),
                credential.getPassword()
        ).orElseThrow(InvalidAuthorizationException::new);
    }
}
