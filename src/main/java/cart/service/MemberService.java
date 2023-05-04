package cart.service;

import java.util.List;

import cart.controller.response.MemberResponse;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.service.dto.MemberUpdateRequest;

public interface MemberService {
	MemberId insert(final MemberUpdateRequest request);
	List<MemberResponse> findAll();
	Member findByMemberId(final MemberId memberId);
	Member findByEmail(final String email);
}
