package cart.service;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.dto.ResponseMemberDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<ResponseMemberDto> findAll() {
        final List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(ResponseMemberDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long findIdByEmail(final String email) {
        final MemberEntity memberEntity = memberDao.findByEmail(email);
        return memberEntity.getId();
    }
}
