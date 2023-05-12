package cart.auth;

import cart.dao.MemberDao;
import cart.domain.entity.Member;
import cart.dto.MemberDto;
import cart.exception.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final MemberDao memberDao;

    public AuthenticationService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void checkAuthenticatedMember(final MemberDto memberDto) {
        Member member = memberDao.selectByEmail(memberDto.getEmail());
        if (!member.isSamePassword(memberDto.getPassword())) {
            throw new AuthenticationException("사용자 인증이 필요합니다.");
        }
    }

}
