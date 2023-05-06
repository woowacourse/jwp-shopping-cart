package cart.service;

import cart.dao.member.MemberDao;
import cart.domain.member.Member;
import cart.dto.MemberResponse;
import cart.mapper.MemberResponseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public Member find(final String email, final String password) {
        return memberDao.findByEmailAndPassword(email, password);
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberDao.findAll();
    }
}
