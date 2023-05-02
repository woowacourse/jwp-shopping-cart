package cart.domain.member.service;

import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.dto.MemberCreateRequest;
import cart.dto.MemberCreateResponse;
import cart.dto.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberService(final MemberDao memberDao, final PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public MemberCreateResponse create(final MemberCreateRequest request) {
        memberDao.findByEmail(request.getEmail())
            .ifPresent(member -> {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            });
        final Member madeMember = request.makeMember();
        madeMember.setPassword(passwordEncoder.encode(madeMember.getPassword()));
        final Member member = memberDao.save(madeMember);
        return MemberCreateResponse.of(member);
    }

    public List<MemberResponse> findAll() {
        final List<Member> members = memberDao.findAll();
        return members.stream()
            .map(MemberResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }
}
