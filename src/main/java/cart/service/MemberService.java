package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        List<Member> members = memberDao.findAll();
        return members.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}
