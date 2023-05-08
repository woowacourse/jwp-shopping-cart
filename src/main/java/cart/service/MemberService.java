package cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.persistence.dao.MemberDao;
import cart.persistence.entity.Member;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<Member> findAll() {
        return memberDao.findAll();
    }
}
