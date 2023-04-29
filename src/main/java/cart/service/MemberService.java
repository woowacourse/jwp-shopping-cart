package cart.service;

import cart.domain.Member;
import cart.exception.DuplicateEmailException;
import cart.repository.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void signupMember(String email, String password) {
        validateEmail(email);
        memberDao.save(new Member(email, password));
    }

    private void validateEmail(String email) {
        if (memberDao.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
