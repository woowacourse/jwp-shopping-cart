package cart.member.service;

import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;

    @Transactional(readOnly = true)
    public Member find(final String email, final String password) {
        return memberDao.findByEmailAndPassword(email, password);
    }

    @Transactional(readOnly = true)
    public Member find(final Long memberId) {
        return memberDao.findById(memberId);
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberDao.findAll();
    }
}
