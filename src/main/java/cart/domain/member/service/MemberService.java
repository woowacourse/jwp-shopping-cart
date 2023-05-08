package cart.domain.member.service;

import cart.dao.MemberDao;
import cart.domain.member.dto.MemberCreateDto;
import cart.domain.member.dto.MemberInformation;
import cart.domain.member.dto.MemberResponse;
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
    public MemberCreateDto create(final MemberInformation memberInformation) {
        checkMemberExist(memberInformation.getEmail());
        final Member member = new Member(null, memberInformation.getEmail(),
            memberInformation.getPassword(), null, null);
        final Member savedMember = memberDao.save(member);
        return new MemberCreateDto(savedMember.getId(), savedMember.getEmail(),
            savedMember.getCreatedAt(), savedMember.getUpdatedAt());
    }

    private void checkMemberExist(final String email) {
        memberDao.findByEmail(email)
            .ifPresent(member -> {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            });
    }

    public List<MemberResponse> findAll() {
        final List<Member> members = memberDao.findAll();
        return members.stream()
            .map(MemberResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }
}
