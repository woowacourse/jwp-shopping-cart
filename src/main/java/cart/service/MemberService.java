package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberDto;
import cart.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberDto> findAll() {
        return memberDao.findAll()
                .stream()
                .map(it -> new MemberDto(it.getEmail(), it.getPassword()))
                .collect(Collectors.toList());
    }

    public int findMemberId(String email, String password) {
        MemberEntity member = memberDao.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return member.getId();
    }
}
