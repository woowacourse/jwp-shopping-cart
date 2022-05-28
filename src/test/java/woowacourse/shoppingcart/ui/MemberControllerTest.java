package woowacourse.shoppingcart.ui;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import woowacourse.auth.application.AuthService;
import woowacourse.auth.domain.Member;
import woowacourse.auth.service.MemberService;
import woowacourse.auth.dto.MemberRequest;
import woowacourse.auth.ui.MemberController;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MemberService memberService;
	@MockBean
	private AuthService authService;

	@DisplayName("회원가입을 한다.")
	@Test
	void signUp() throws Exception {
		// given
		MemberRequest request = new MemberRequest("123@gmail.com", "1234");
		String requestJson = objectMapper.writeValueAsString(request);
		given(memberService.signUp(any(MemberRequest.class)))
				.willReturn(new Member(1L, "123@gmail.com", "1234"));

		// when
		mockMvc.perform(post("/members")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson))
			.andExpect(status().isCreated());
	}

	@DisplayName("중복된 이메일이 있으면 가입하지 못한다.")
	@Test
	void signUpDuplicatedEmail() throws Exception {
		// given
		MemberRequest request = new MemberRequest("123@gmail.com", "1234");
		String requestJson = objectMapper.writeValueAsString(request);
		given(memberService.signUp(any(MemberRequest.class)))
			.willThrow(IllegalArgumentException.class);

		// when
		mockMvc.perform(post("/members")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isBadRequest());
	}
}
