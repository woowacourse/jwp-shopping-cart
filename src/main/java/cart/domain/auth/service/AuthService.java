package cart.domain.auth.service;

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

    public boolean checkAuthenticationHeader(final String header) {
        final AuthInfo authInfo = authorizationExtractor.extract(header);
        final Member member = memberDao.findByEmail(authInfo.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("인증 실패"));
        return member.getPassword().equals(authInfo.getPassword());
    }
}
