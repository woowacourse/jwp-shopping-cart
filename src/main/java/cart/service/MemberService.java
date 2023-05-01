package cart.service;

import cart.service.request.MemberCreateRequest;
import cart.service.response.MemberResponse;

import java.util.List;

public interface MemberService {
    long save(final MemberCreateRequest request);
    List<MemberResponse> findAll();
    long deleteByMemberId(final long memberId);
}
