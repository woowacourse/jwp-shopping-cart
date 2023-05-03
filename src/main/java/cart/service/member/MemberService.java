package cart.service.member;

import cart.dao.member.MemberDao;
import cart.dto.member.MemberResponse;
import cart.entity.member.Member;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAll() {
        List<Member> members = memberDao.findAll();
        return members.stream()
            .map(MemberResponse::new)
            .collect(Collectors.toUnmodifiableList());
    }
}
