package cart.service;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import cart.exception.auth.UnauthenticatedException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Transactional(readOnly = true)
    public Member login(String email, String password) {
        Optional<Member> member = memberDao.findByEmailAndPassword(email, password);
        if (member.isEmpty()) {
            throw new UnauthenticatedException();
        }
        return member.get();
    }
}
