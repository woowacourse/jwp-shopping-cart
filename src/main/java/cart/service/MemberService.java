package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberResponse;
import cart.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }


    public List<MemberResponse> selectAllMember() {
        List<Member> members = memberDao.selectAll();
        return members.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
