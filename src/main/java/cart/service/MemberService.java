package cart.service;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.exception.custom.InvalidPasswordException;
import java.util.List;
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

    //TODO naming && 로직 고민
    public void isMatch(String email, String password) {
        Member member = memberDao.findByEmail(email);
        if (!member.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }
    }
}
