package cart.service;

import static org.mockito.ArgumentMatchers.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cart.service.response.MemberResponse;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.repository.MemberRepository;
import cart.service.request.MemberUpdateRequest;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

	@Mock
	MemberRepository memberRepository;

	@InjectMocks
	MemberServiceImpl memberService;

	@DisplayName("회원 저장 테스트")
	@Test
	void insert() {
		// given
		BDDMockito.given(memberRepository.insert(any())).willReturn(MemberId.from(1L));

		// when
		final MemberUpdateRequest request = new MemberUpdateRequest("kiara", "email@email", "password");
		final MemberId memberId = memberService.insert(request);

		// then
		Assertions.assertThat(MemberId.from(1L)).isEqualTo(memberId);
	}

	@Test
	void findAll() {
		// given
		final List<Member> members = List.of(new Member(MemberId.from(1L), "kiara", "email@email", "pw"));

		BDDMockito.given(memberRepository.findAll()).willReturn(members);

		// when
		final List<MemberResponse> findAll = memberService.findAll();

		// then
		Assertions.assertThat(findAll)
			.usingRecursiveComparison()
			.isEqualTo(List.of(new MemberResponse(1L, "kiara", "email@email", "pw")));
	}

	@Test
	void findByMemberId() {
	}

	@Test
	void findByEmail() {
	}
}
