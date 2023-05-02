package cart.service;

import cart.annotation.ServiceWithTransactionalReadOnly;
import cart.controller.dto.response.MemberResponse;
import cart.dao.MemberDao;

import java.util.List;
import java.util.stream.Collectors;

@ServiceWithTransactionalReadOnly
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAll() {
        return memberDao.findAll().stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }

}
