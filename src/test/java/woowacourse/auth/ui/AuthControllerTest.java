package woowacourse.auth.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import woowacourse.auth.application.CustomerService;
import woowacourse.auth.dto.customer.CustomerRequest;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.auth.dto.token.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;

public class AuthControllerTest extends ControllerTest {

	@Autowired
	private CustomerService customerService;
	@Autowired
	protected JwtTokenProvider tokenProvider;

	@DisplayName("로그인이 성공한다.")
	@Test
	void login() throws Exception {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));

		// when
		ResultActions result = mockMvc.perform(post("/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(new TokenRequest(email, password))));

		// then
		String responseJson = result.andReturn()
			.getResponse()
			.getContentAsString();
		TokenResponse tokenResponse = objectMapper.readValue(responseJson, TokenResponse.class);
		assertAll(
			() -> result.andExpect(status().isOk()),
			() -> assertThat(tokenResponse.getNickname()).isEqualTo(nickname),
			() -> assertThat(tokenProvider.getPayload(tokenResponse.getAccessToken())).isEqualTo(email)
		);
	}

	@DisplayName("비밀번호가 틀려 로그인에 실패한다.")
	@Test
	void loginFailPassword() throws Exception {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));

		// when
		ResultActions result = mockMvc.perform(post("/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(
				new TokenRequest(email, "a12232134!"))));

		// then
		result.andExpect(status().isUnauthorized());
	}

	@DisplayName("email이 틀려 로그인에 실패한다.")
	@Test
	void loginFail() throws Exception {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));

		// when
		ResultActions result = mockMvc.perform(post("/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(
				new TokenRequest("email@gmail.com", password))));

		// then
		result.andExpect(status().isUnauthorized());
	}
}
