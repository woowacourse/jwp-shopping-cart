package cart.business;

import cart.business.domain.Member;
import cart.business.domain.Members;
import cart.entity.MemberEntity;
import cart.persistence.MemberDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private MemberDao memberDao;
    private Members members;

    public MemberService(MemberDao memberDao) {
        this.members = new Members(new ArrayList<>());
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberEntity> read() {
        return memberDao.findAll();
    }

    public Optional<MemberEntity> findMemberByEmail(String email) {
        return memberDao.findByEmail(email);
    }

    @Transactional
    public Integer delete(Integer id) {
        Member member = members.findMemberById(id);
        members.removeMember(member);

        return memberDao.remove(member);
    }
}
