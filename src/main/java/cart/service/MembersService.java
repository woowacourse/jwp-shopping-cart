package cart.service;

import cart.domain.member.Member;
import cart.dto.response.MemberResponse;
import cart.persistence.MemberDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MembersService {

    private final MemberDao memberDao;

    public MembersService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> readAll() {
        List<Member> members = memberDao.findAll();

        return members.stream()
                .map(member -> new MemberResponse(member.getEmail(), member.getPassword()))
                .collect(Collectors.toList());
    }
}
