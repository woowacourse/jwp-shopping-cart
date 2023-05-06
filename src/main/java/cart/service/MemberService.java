package cart.service;

import cart.dto.MemberDto;
import cart.dao.MemberDao;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberDao memberDao;

    @Transactional(readOnly = true)
    public List<MemberDto> findAll() {
        return memberDao.findAll().stream()
                .map(MemberDto::fromEntity)
                .collect(Collectors.toList());
    }
}
