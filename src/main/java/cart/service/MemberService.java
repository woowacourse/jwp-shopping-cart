package cart.service;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import cart.dto.application.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberDto> findAll() {
        return memberDao.findAll().stream()
                .map(member -> new MemberDto(member.getUsername(), member.getPassword()))
                .collect(Collectors.toList());
    }

    public boolean isMember(final MemberDto memberDto) {
        final Member member = new Member(memberDto.getUsername(), memberDto.getPassword());
        return memberDao.isMember(member);
    }

    public long findMemberId(final MemberDto memberDto) {
        final Member member = new Member(memberDto.getUsername(), memberDto.getPassword());
        return memberDao.findMemberId(member);
    }
}
