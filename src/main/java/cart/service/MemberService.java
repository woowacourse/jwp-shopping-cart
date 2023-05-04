package cart.service;

import cart.dto.AuthorizationInformation;
import cart.dao.MemberDao;
import cart.entity.AuthMember;
import cart.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void save(AuthorizationInformation authorizationInformation) {
        AuthMember authMember = authorizationInformation.toAuthMember();

        memberDao.save(authMember);
    }

    public boolean isValidMember(AuthorizationInformation authorizationInformation) {
        AuthMember authMember = authorizationInformation.toAuthMember();

        return memberDao.isMemberExists(authMember);
    }

    public List<Member> findAll() {
        return memberDao.findAll();
    }
}
