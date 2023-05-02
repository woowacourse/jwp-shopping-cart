package cart.member.service;

import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import cart.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberMemoryService implements MemberService {
    private final MemberDao memberDao;
    
    @Override
    public List<MemberResponse> findAll() {
        return memberDao.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
    
    @Override
    public MemberResponse findByEmailAndPassword(final String email, final String password) {
        final Member member = memberDao.findByEmailAndPassword(email, password);
        return MemberResponse.from(member);
    }
}
