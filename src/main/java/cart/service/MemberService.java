package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberDto;
import cart.entity.MemberEntity;
import java.util.List;
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

}
