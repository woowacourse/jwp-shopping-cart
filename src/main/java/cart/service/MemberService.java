package cart.service;

import cart.authorization.AuthInformation;
import cart.dao.MemberDao;
import cart.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public boolean isValidMember(AuthInformation authInformation) {
        Member member = convertAuthinformationToMember(authInformation);

        return memberDao.isMemberExists(member);
    }

    private static Member convertAuthinformationToMember(AuthInformation authInformation) {
        Member member = new Member(authInformation.getEmail(), authInformation.getPassword());
        return member;
    }
}
