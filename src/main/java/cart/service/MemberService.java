package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberResponse;
import cart.entity.Member;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findMembers() {
        return memberDao.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    public Member findMember(String email) {
        return memberDao.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("찾는 회원이 없습니다"));
    }
}
