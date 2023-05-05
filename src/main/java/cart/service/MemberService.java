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

    public long save(MemberRequest memberRequest) {
        return memberDao.save(new Member(null, memberRequest.getEmail(), memberRequest.getPassword()));
    }

    public List<MemberResponse> findAll() {
        return memberDao.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}
