package cart.service;

import cart.entity.member.Member;
import cart.entity.member.MemberDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberFindService {
    private final MemberDao dbMemberDao;

    public MemberFindService(final MemberDao dbMemberDao) {
        this.dbMemberDao = dbMemberDao;
    }

    public List<Member> findAll() {
        return dbMemberDao.findAll();
    }
}
