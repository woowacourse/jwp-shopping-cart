package cart.service;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import cart.dto.application.MemberDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<Member> fetchAll() {
        return memberDao.fetchAll();
    }

    public boolean isMember(final MemberDto memberDto) {
        final Member member = new Member(memberDto.getUsername(), memberDto.getPassword());
        return memberDao.isMember(member);
    }
}
