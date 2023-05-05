package cart.service;

import cart.dao.Dao;
import cart.domain.Member;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final Dao<Member> memberDao;

    public MemberService(final Dao<Member> memberDao) {
        this.memberDao = memberDao;
    }

    public List<Member> findAll() {
        return memberDao.findAll();
    }
}
