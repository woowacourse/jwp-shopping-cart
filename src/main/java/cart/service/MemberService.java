package cart.service;

import cart.authorization.AuthorizationInformation;
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

    public boolean isValidMember(AuthorizationInformation authorizationInformation) {
        AuthMember authMember = convertAuthInformationToMember(authorizationInformation);

        return memberDao.isMemberExists(authMember);
    }

    public List<Member> findAll() {
        return memberDao.findAll();
    }

    private static AuthMember convertAuthInformationToMember(AuthorizationInformation authorizationInformation) {
        return new AuthMember(authorizationInformation.getEmail(), authorizationInformation.getPassword());
    }
}
