package cart.service.member;

import cart.dao.member.MemberDao;
import cart.dto.member.MemberInfoResponse;
import cart.entity.member.Member;
import cart.exception.notfound.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberInfoResponse> findAll() {
        List<Member> members = memberDao.findAll();
        return members.stream()
            .map(MemberInfoResponse::new)
            .collect(Collectors.toUnmodifiableList());
    }

    public Member findByEmailAndPassword(String email, String password) {
        return memberDao.findByEmailAndPassword(email, password)
            .orElseThrow(MemberNotFoundException::new);
    }
}
