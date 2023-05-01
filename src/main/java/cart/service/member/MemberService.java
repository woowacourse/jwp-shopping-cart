package cart.service.member;

import cart.domain.member.MemberId;
import cart.service.request.MemberCreateRequest;
import cart.service.response.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberId save(final MemberCreateRequest request);

    List<MemberResponse> findAll();

    MemberId deleteByMemberId(final MemberId memberId);
}
