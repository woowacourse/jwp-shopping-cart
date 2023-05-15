package cart.service;

import cart.dao.member.MemberDao;
import cart.domain.member.Member;
import cart.dto.member.ResponseMemberDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<ResponseMemberDto> display() {
        final List<Member> members = memberDao.findAll();
        return members.stream()
                .map(ResponseMemberDto::create)
                .collect(Collectors.toList());
    }
}
