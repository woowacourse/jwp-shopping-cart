package cart.service.member;

import cart.entity.member.Member;
import cart.entity.member.MemberDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberFindService {
    private final MemberDao dbMemberDao;

    public MemberFindService(final MemberDao dbMemberDao) {
        this.dbMemberDao = dbMemberDao;
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return dbMemberDao.findAll();
    }
}
