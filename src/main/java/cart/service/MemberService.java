package cart.service;

import java.util.List;

import cart.service.response.MemberResponse;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.service.request.MemberUpdateRequest;

public interface MemberService {
	MemberId insert(final MemberUpdateRequest request);
	List<MemberResponse> findAll();
	MemberResponse findByMemberId(final MemberId memberId);
	Member findByEmail(final String email);
}
