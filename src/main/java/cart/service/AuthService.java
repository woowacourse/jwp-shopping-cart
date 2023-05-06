package cart.service;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.request.AuthRequest;
import cart.dto.request.Credential;
import cart.exception.custom.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    //TODO: 리턴 객체에 대한 고민
    @Transactional(readOnly = true)
    public Credential findCredential(AuthRequest authRequest) {
        Member member = memberDao.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UnauthorizedException("해당하는 email의 회원이 존재하지 않습니다."));
        if (member.isWrongPassword(authRequest.getPassword())) {
            throw new UnauthorizedException("패스워드가 일치하지 않습니다.");
        }
        return new Credential(member.getId(), authRequest.getEmail(), authRequest.getPassword());
    }
}
