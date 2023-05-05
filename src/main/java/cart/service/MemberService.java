package cart.service;

import cart.dao.ReadOnlyDao;
import cart.domain.Email;
import cart.domain.Member;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final ReadOnlyDao<Member, Email> memberDao;

    public MemberService(final ReadOnlyDao<Member, Email> memberDao) {
        this.memberDao = memberDao;
    }

    public List<Member> findAll() {
        return memberDao.findAll();
    }
}
