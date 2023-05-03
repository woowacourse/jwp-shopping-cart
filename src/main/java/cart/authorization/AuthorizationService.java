package cart.authorization;

import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {

    private final MemberDao memberDao;

    @Autowired
    public AuthorizationService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public long authenticateMember(AuthInfo authInfo) {
        final Optional<Member> foundMember = memberDao.findByEmailWithPassword(authInfo.getEmail(), authInfo.getPassword());

        return foundMember.orElseThrow(() -> new UnauthorizedMemberException("인증되지 않은 사용자입니다")).getId();
    }
}
