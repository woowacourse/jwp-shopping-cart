package cart.service;

import cart.authorization.AuthInformation;
import cart.dao.MemberDao;
import cart.entity.AuthMember;
import cart.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public boolean isValidMember(AuthInformation authInformation) {
        AuthMember authMember = convertAuthInformationToMember(authInformation);

        return memberDao.isMemberExists(authMember);
    }

    public List<Member> findAll() {
        return memberDao.findAll();
    }

    private static AuthMember convertAuthInformationToMember(AuthInformation authInformation) {
        AuthMember authMember = new AuthMember(authInformation.getEmail(), authInformation.getPassword());
        return authMember;
    }
}
