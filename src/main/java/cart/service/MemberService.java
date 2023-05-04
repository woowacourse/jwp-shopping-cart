package cart.service;

import cart.dto.MemberDto;
import cart.repository.dao.MemberDao;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberDto> findAllMember() {
        return memberDao.findAll().stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
    }
}
