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
        Member member = memberDao.selectByEmail(memberDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!member.isSamePassword(memberDto.getPassword())) {
            throw new AuthenticationException("사용자를 찾을 수 없습니다.");
        }
    }

}
