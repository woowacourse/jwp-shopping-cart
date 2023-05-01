package cart.member.service;

import cart.member.dao.MemberDao;
import cart.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberDao memberDao;
    
    public List<MemberResponse> findAll() {
        return memberDao.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
