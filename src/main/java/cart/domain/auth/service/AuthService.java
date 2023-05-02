package cart.domain.auth.service;

import cart.common.AuthenticationException;
import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.dto.AuthInfo;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthorizationExtractor authorizationExtractor;
    private final MemberDao memberDao;

    public AuthService(final AuthorizationExtractor authorizationExtractor,
        final MemberDao memberDao) {
        this.authorizationExtractor = authorizationExtractor;
        this.memberDao = memberDao;
    }

    public AuthInfo checkAuthenticationHeader(final String header) {
        final AuthInfo authInfo = authorizationExtractor.extract(header);
        final Member member = memberDao.findByEmail(authInfo.getEmail())
            .orElseThrow(() -> new AuthenticationException("인증 실패"));
        if (!member.getPassword().equals(authInfo.getPassword())) {
            throw new AuthenticationException("인증 실패");
        }
        return authInfo;
    }
}
