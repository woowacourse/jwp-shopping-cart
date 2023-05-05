package cart.service;

import cart.dao.MemberDao;
import cart.entity.MemberEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberEntity> selectAllMembers() {
        return memberDao.selectAllMembers();
    }

    public int addMember(MemberEntity memberEntity) {
        return memberDao.addMember(memberEntity);
    }

    public int findMemberId(String email, String password) {
        if (!memberDao.isMemberExist(email, password)) {
            throw new IllegalArgumentException("해당하는 유저가 존재하지 않습니다.");
        }

        return memberDao.findMemberId(email, password);
    }

    public void deleteAllMembers() {
        memberDao.deleteAllMembers();
    }
}
