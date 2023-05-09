package cart.auth;

import cart.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.domain.member.dto.MemberDto;
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

    public MemberDto checkAuthenticationHeader(final String header) {
        final MemberDto memberDto = authorizationExtractor.extract(header);
        final Member member = memberDao.findByEmail(memberDto.getEmail())
            .orElseThrow(() -> new AuthenticationException("인증 실패"));
        if (member.passwordNotEquals(memberDto.getPassword())) {
            throw new AuthenticationException("인증 실패");
        }
        return memberDto;
    }
}
