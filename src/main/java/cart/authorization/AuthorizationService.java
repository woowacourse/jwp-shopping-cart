package cart.authorization;

import cart.exception.UnauthorizedMemberException;
import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Transactional
public class AuthorizationService {

    private final MemberDao memberDao;
    private final AuthorizationExtractor<MemberAuthorizationInfo> authorizationExtractor;

    @Autowired
    public AuthorizationService(MemberDao memberDao, AuthorizationExtractor<MemberAuthorizationInfo> authorizationExtractor) {
        this.memberDao = memberDao;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Transactional(readOnly = true)
    public long authorizeMember(HttpServletRequest request) {
        final MemberAuthorizationInfo memberAuthorizationInfo = authorizationExtractor.extract(request);
        final Optional<Member> foundMember = memberDao.findByEmailWithPassword(memberAuthorizationInfo.getEmail(), memberAuthorizationInfo.getPassword());

        return foundMember.orElseThrow(() -> new UnauthorizedMemberException("인증되지 않은 사용자입니다")).getId();
    }
}
