package cart.service;

import cart.dao.member.MemberDao;
import cart.domain.member.Member;
import cart.dto.MemberResponse;
import cart.mapper.MemberResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberManagementService {

    private final MemberDao memberDao;

    public MemberManagementService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAll() {
        final List<Member> members = memberDao.selectAll();
        return MemberResponseMapper.from(members);
    }
}
