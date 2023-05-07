package cart.auth.application;

import cart.domain.member.Member;
import cart.repository.MemberRepository;
import cart.service.response.MemberResponse;

public class AuthService {
	private final MemberRepository memberRepository;

	public AuthService(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public MemberResponse findMember(final String email) {
		final Member member = memberRepository.findByEmail(email);
		return new MemberResponse(member.getId().getId(), member.getName(), member.getEmail(), member.getPassword());
	}
}
