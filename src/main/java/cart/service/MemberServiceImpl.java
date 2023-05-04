package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.service.response.MemberResponse;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.repository.MemberRepository;
import cart.service.request.MemberUpdateRequest;

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
		return memberRepository.findAll()
			.stream()
			.map(member -> new MemberResponse(member.getId().getId(), member.getName(), member.getEmail(),
				member.getPassword()))
			.collect(Collectors.toList());
	}

	@Override
	public MemberResponse findByMemberId(final MemberId memberId) {
		final Member member = memberRepository.findByMemberId(memberId);
		return new MemberResponse(member.getId().getId(), member.getName(), member.getEmail(), member.getPassword());
	}

	@Override
	public Member findByEmail(final String email) {
		return null;
	}
}
