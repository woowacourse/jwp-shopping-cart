package cart.business;

import cart.entity.Member;
import cart.persistence.MemberDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<Member> read() {
        return memberDao.findAll();
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberDao.findByEmail(email);
    }

    @Transactional
    public Integer delete(Integer id) {
        return memberDao.remove(id);
    }
}
