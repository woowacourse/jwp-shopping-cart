package cart.service;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.MemberRequest;
import cart.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberDao memberDao;

    public long save(final MemberRequest memberRequest) {
        return memberDao.save(new Member(memberRequest.getEmail(), memberRequest.getPassword()));
    }

    public List<MemberResponse> findAll() {
        return memberDao.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    public MemberResponse findByEmail(final String email) {
        return MemberResponse.from(memberDao.findByEmail(email));
    }
}
