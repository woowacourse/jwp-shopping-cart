package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAll() {
        List<Member> members = memberDao.findAll();
        return members.stream()
                .map(member -> new MemberResponse(member.getEmail(), member.getPassword()))
                .collect(Collectors.toList());
    }
}
