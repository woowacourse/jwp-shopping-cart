package cart.service;

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

    public boolean isValidMember(Member member) {
        return memberDao.isMemberExists(member);
    }
}
