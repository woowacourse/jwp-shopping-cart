package cart.domain.auth.service;

import cart.common.AuthenticationException;
import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.dto.MemberInformation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final AuthorizationExtractor authorizationExtractor;
    private final MemberDao memberDao;

    public AuthService(final AuthorizationExtractor authorizationExtractor,
        final MemberDao memberDao) {
        this.authorizationExtractor = authorizationExtractor;
        this.memberDao = memberDao;
    }

    public MemberInformation checkAuthenticationHeader(final String header) {
        final MemberInformation memberInformation = authorizationExtractor.extract(header);
        final Member member = memberDao.findByEmail(memberInformation.getEmail())
            .orElseThrow(() -> new AuthenticationException("인증 실패"));
        if (!member.getPassword().equals(memberInformation.getPassword())) {
            throw new AuthenticationException("인증 실패");
        }
        return memberInformation;
    }
}
