package cart.domain.member.service;

import cart.dao.MemberDao;
import cart.domain.member.dto.CreatedMemberDto;
import cart.domain.member.dto.MemberDto;
import cart.domain.member.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public CreatedMemberDto create(final MemberDto memberDto) {
        checkMemberExist(memberDto.getEmail());
        final Member member = new Member(null, memberDto.getEmail(),
            memberDto.getPassword(), null, null);
        final Member savedMember = memberDao.save(member);
        return new CreatedMemberDto(savedMember.getId(), savedMember.getEmail(),
            savedMember.getCreatedAt(), savedMember.getUpdatedAt());
    }

    private void checkMemberExist(final String email) {
        memberDao.findByEmail(email)
            .ifPresent(member -> {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            });
    }

    public List<MemberDto> findAll() {
        final List<Member> members = memberDao.findAll();
        return members.stream()
            .map(member -> new MemberDto(member.getEmail(), member.getPassword()))
            .collect(Collectors.toUnmodifiableList());
    }
}
