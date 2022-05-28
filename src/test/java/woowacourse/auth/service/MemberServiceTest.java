package woowacourse.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Member;
import woowacourse.auth.dto.MemberRequest;
import woowacourse.auth.exception.InvalidMemberException;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	private MemberDao memberDao;
	@InjectMocks
	private MemberService memberService;

	@DisplayName("회원 정보를 저장한다.")
	@Test
	void sighUp() {
		// given
		MemberRequest request = new MemberRequest("123@gmail.com", "!234");
		Member member = new Member(1L, "123@gmail.com", "!234");
		given(memberDao.save(any(Member.class)))
			.willReturn(member);

		// when
		Member saved = memberService.signUp(request);

		// then
		assertThat(saved).isEqualTo(member);
	}

	@DisplayName("중복 이메일은 저장하지 못 한다.")
	@Test
	void emailDuplicate() {
		// given
		MemberRequest request = new MemberRequest("123@gmail.com", "!234");
		given(memberDao.existByEmail("123@gmail.com"))
			.willReturn(true);

		// then
		assertThatThrownBy(() -> memberService.signUp(request))
			.isInstanceOf(InvalidMemberException.class);
	}
}
