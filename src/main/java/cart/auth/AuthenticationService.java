package cart.auth;

import cart.dao.MemberDao;
import cart.dto.MemberDto;
import cart.entity.MemberEntity;
import cart.exception.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final MemberDao memberDao;

    public AuthenticationService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberDto login(final MemberAuthentication memberAuthentication) {
        MemberEntity memberEntity = memberDao.selectByEmailAndPassword(MemberAuthentication.toEntity(memberAuthentication));
        if (memberEntity == null) {
            throw new AuthenticationException("사용자 인증에 실패하였습니다.");
        }
        return MemberDto.from(memberEntity);
    }
}
