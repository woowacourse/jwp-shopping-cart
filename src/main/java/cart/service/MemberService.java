package cart.service;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MemberService {

    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void createMember(MemberDto request) {
        Member member = new Member(request.getEmail(), request.getPassword());
        memberDao.save(member);
    }

    public List<MemberDto> findAll() {
        List<Member> members = memberDao.findAll();
        return members.stream()
                .map(m -> new MemberDto(m.getEmail(), m.getPassword()))
                .collect(Collectors.toList());
    }
}
