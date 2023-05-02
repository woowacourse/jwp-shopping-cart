package cart.domain.member.service;

import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.dto.MemberCreateRequest;
import cart.dto.MemberCreateResponse;

public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberCreateResponse create(final MemberCreateRequest request) {
        memberDao.findByEmail(request.getEmail())
            .ifPresent(member -> {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            });
        final Member member = memberDao.save(request.makeMember());
        return MemberCreateResponse.of(member);
    }
}
