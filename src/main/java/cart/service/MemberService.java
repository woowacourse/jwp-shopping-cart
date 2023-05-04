package cart.service;

import java.util.List;

import cart.domain.member.MemberId;
import cart.service.request.MemberUpdateRequest;
import cart.service.response.MemberResponse;

public interface MemberService {
	MemberId insert(final MemberUpdateRequest request);
	List<MemberResponse> findAll();
	MemberResponse findByMemberId(final MemberId memberId);
	MemberResponse findByEmail(final String email);
}
