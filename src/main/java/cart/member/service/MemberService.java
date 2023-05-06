package cart.member.service;

import cart.member.domain.MemberId;
import cart.member.service.request.MemberCreateRequest;
import cart.member.service.response.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberId save(final MemberCreateRequest request);

    List<MemberResponse> findAll();

    MemberId deleteByMemberId(final MemberId memberId);
}
