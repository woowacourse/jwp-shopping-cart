package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
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

    public List<MemberResponse> findAll() {
        final List<MemberEntity> memberEntities = memberDao.findAll();
        return memberEntities.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
