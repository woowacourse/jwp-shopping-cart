package cart.service;

import cart.dao.MemberDao;
import cart.domain.entity.MemberEntity;
import cart.dto.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberManagementService {

    private final MemberDao memberDao;

    public MemberManagementService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberDto> findAll() {
        final List<MemberEntity> memberEntities = memberDao.selectAll();
        return MemberDto.from(memberEntities);
    }

}
