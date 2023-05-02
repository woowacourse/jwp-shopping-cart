package cart.member.service;

import cart.member.dao.MemberDao;
import cart.member.dto.MemberDto;
import cart.member.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberDto> getMembers() {
        final List<MemberEntity> members = memberDao.selectAll();
        return members.stream()
                .map(MemberMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public MemberEntity findMemberByEmail(String memberEmail) {
        final Optional<MemberEntity> findMember = memberDao.findByEmail(memberEmail);

        if (findMember.isEmpty()) {
            throw new IllegalArgumentException("입력된 E-mail에 해당하는 사용자가 존재하지 않습니다.");
        }
        return findMember.get();
    }
}
