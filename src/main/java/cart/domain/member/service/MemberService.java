package cart.domain.member.service;

import cart.dao.MemberDao;
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

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public MemberCreateResponse create(final MemberCreateRequest request) {
        checkMemberExist(request);
        final Member requestMember = request.makeMember();
        final Member savedMember = memberDao.save(requestMember);
        return MemberCreateResponse.of(savedMember);
    }

    private void checkMemberExist(final MemberCreateRequest request) {
        memberDao.findByEmail(request.getEmail())
            .ifPresent(member -> {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            });
    }

    public List<MemberResponse> findAll() {
        final List<Member> members = memberDao.findAll();
        return members.stream()
            .map(MemberResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }
}
