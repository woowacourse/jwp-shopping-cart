package cart.service;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.domain.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberDto> getMembers() {
        List<Member> members = memberDao.findAll().stream()
                .map(memberEntity -> new Member(
                        memberEntity.getId(),
                        memberEntity.getEmail(),
                        memberEntity.getPassword()
                )).collect(Collectors.toList());

        return members.stream()
                .map(member -> new MemberDto(
                        member.getId(),
                        member.getEmail(),
                        member.getPassword()
                )).collect(Collectors.toList());
    }
}
