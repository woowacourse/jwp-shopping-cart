package cart.service.member;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public long createMember(String email, String password) {
        Member member = new Member(email, password);
        memberDao.findByEmail(email).ifPresent((e) -> {
            throw new IllegalArgumentException("해당 이메일은 이미 존재합니다.");
        });
        member = memberDao.save(member);
        return member.getId();
    }

    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberDao.findAll();
        return members.stream()
                .map(m -> new MemberResponse(m.getId(), m.getEmail(), m.getPassword()))
                .collect(Collectors.toList());
    }
}
