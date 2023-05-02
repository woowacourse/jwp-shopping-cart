package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponseDto> findAll() {
        return memberDao.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

}
