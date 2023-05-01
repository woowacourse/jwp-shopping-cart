package cart.service;

import cart.dao.MemberDao;
import cart.dto.response.MemberResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findMembers() {
        return memberDao.findAll().stream()
                .map(MemberResponseDto::from)
                .collect(Collectors.toList());
    }
}
