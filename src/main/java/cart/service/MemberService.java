package cart.service;

import cart.dao.member.MemberDao;
import cart.dao.member.MemberEntity;
import cart.service.dto.member.MemberSearchResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberSearchResponse> searchAllMembers() {
        List<MemberEntity> memberEntities = memberDao.findAll();

        return memberEntities.stream()
                .map(memberEntity -> new MemberSearchResponse(
                        memberEntity.getId(),
                        memberEntity.getEmail(),
                        memberEntity.getPassword()
                ))
                .collect(Collectors.toList());
    }
}
