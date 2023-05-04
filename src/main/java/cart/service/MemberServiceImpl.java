package cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.controller.response.MemberResponse;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.repository.MemberRepository;
import cart.service.dto.MemberUpdateRequest;

@Service
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;

	public MemberServiceImpl(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public MemberId insert(final MemberUpdateRequest request) {
		return memberRepository.insert(new Member(request.getName(), request.getEmail(), request.getPassword()));
	}

	@Override
	public List<MemberResponse> findAll() {
		return null;
	}

	@Override
	public Member findByMemberId(final MemberId memberId) {
		return null;
	}

	@Override
	public Member findByEmail(final String email) {
		return null;
	}
}
