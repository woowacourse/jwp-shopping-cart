package cart.auth.application;

import cart.domain.member.Member;
import cart.repository.MemberRepository;
import cart.service.response.MemberResponse;

public class AuthService {
	private final MemberRepository memberRepository;

	public AuthService(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public boolean checkInvalidLogin(String principal, String credentials) {
		final Member member = memberRepository.findByEmail(principal);
		return !member.getEmail().equals(principal) || !member.getPassword().equals(credentials);
	}

	public MemberResponse findMember(final String email) {
		final Member member = memberRepository.findByEmail(email);
		return new MemberResponse(member.getId().getValue(), member.getName(), member.getEmail(), member.getPassword());
	}
}
