package woowacourse.auth.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import woowacourse.auth.application.AuthService;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.CustomerUpdateRequest;
import woowacourse.auth.dto.CustomerUpdateResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

class CustomerControllerTest extends ControllerTest{

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AuthService authService;

	@DisplayName("회원가입을 한다.")
	@Test
	void signUp() throws Exception {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		String requestJson = objectMapper.writeValueAsString(request);

		// when
		ResultActions result = mockMvc.perform(post("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson));

		// then
		result.andExpect(status().isCreated())
			.andExpect(content().json(objectMapper.writeValueAsString(
				new CustomerResponse(email, nickname)))
			);
	}

	@DisplayName("중복된 이메일이 있으면 가입하지 못한다.")
	@Test
	void signUpDuplicatedEmail() throws Exception {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		customerService.signUp(request);
		String requestJson = objectMapper.writeValueAsString(request);

		// when
		ResultActions result = mockMvc.perform(post("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson));

		// then
		result.andExpect(status().isBadRequest());
	}

	@DisplayName("이메일의 형식이 올바르지 못하면 400 반환")
	@ParameterizedTest
	@ValueSource(strings = {"123gmail.com", "123gamilcom", "@gmail.com"})
	void emailFormatException(String email) throws Exception {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		String requestJson = objectMapper.writeValueAsString(request);

		// when
		ResultActions result = mockMvc.perform(post("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson));

		// then
		result.andExpect(status().isBadRequest());
	}

	@DisplayName("닉네임의 형식이 올바르지 못하면 400 반환")
	@ParameterizedTest
	@ValueSource(strings = {"1", "12345678901"})
	void nicknameFormatException(String nickname) throws Exception {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		String requestJson = objectMapper.writeValueAsString(request);

		// when
		ResultActions result = mockMvc.perform(post("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson));

		// then
		result.andExpect(status().isBadRequest());
	}

	@DisplayName("비밀번호의 형식이 올바르지 못하면 400 반환")
	@ParameterizedTest
	@ValueSource(strings = {"1234", "abasdas", "!@#!@#", "123d213", "asdasd!@#@", "123!@@##!1"})
	void passwordFormatException(String password) throws Exception {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		String requestJson = objectMapper.writeValueAsString(request);

		// when
		ResultActions result = mockMvc.perform(post("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson));

		// then
		result.andExpect(status().isBadRequest());
	}

	@DisplayName("토큰이 없을 때 탈퇴를 하려고 하면 401 반환")
	@Test
	void signOutNotLogin() throws Exception {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		customerService.signUp(request);

		// when
		mockMvc.perform(delete("/customers"))
			.andExpect(status().isUnauthorized());
	}

	@DisplayName("토큰이 있을 때 회원 탈퇴를 한다.")
	@Test
	void signOutwithToken() throws Exception {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		customerService.signUp(request);
		TokenResponse token = authService.login(new TokenRequest(email, password));

		// when
		ResultActions result = mockMvc.perform(delete("/customers")
			.header("Authorization", "Bearer " + token.getAccessToken()));

		// then
		result.andExpect(status().isNoContent());
	}

	@DisplayName("토큰이 있을 때 회원정보 수정을 한다.")
	@Test
	void updateCustomer() throws Exception {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));
		TokenResponse tokenResponse = authService.login(new TokenRequest(email, password));

		// when
		CustomerUpdateRequest request = new CustomerUpdateRequest(
			"thor", "a1234!", "b1234!"
		);
		CustomerUpdateResponse response = new CustomerUpdateResponse("thor");

		ResultActions result = mockMvc.perform(patch("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + tokenResponse.getAccessToken())
			.content(objectMapper.writeValueAsString(request)));

		// then
		result.andExpect(content().json(objectMapper.writeValueAsString(response)))
			.andExpect(status().isOk());
	}

	@DisplayName("토큰이 없을 때 회원 정보 수정을 하면 401 반환")
	@Test
	void updateCustomerNotToken() throws Exception {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));

		// when
		CustomerUpdateRequest request = new CustomerUpdateRequest(
			"thor", "a1234!", "b1234!"
		);
		ResultActions result = mockMvc.perform(patch("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		// then
		result.andExpect(status().isUnauthorized());
	}

	@DisplayName("기존 비밀번호가 다르면 정보를 수정할 수 없다.")
	@Test
	void updateCustomerNotSamePassword() throws Exception {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));
		TokenResponse tokenResponse = authService.login(new TokenRequest(email, password));

		// when
		CustomerUpdateRequest request = new CustomerUpdateRequest(
			"thor", "a1234567!", "b1234!"
		);
		ResultActions result = mockMvc.perform(patch("/customers")
			.header("Authorization", "Bearer " + tokenResponse.getAccessToken())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		// then
		result.andExpect(status().isUnauthorized());
	}

	@DisplayName("회원정보를 조회할 수 있다.")
	@Test
	void findCustomer() throws Exception {
		// given
		customerService.signUp(new CustomerRequest(email, password, nickname));
		TokenResponse tokenResponse = authService.login(new TokenRequest(email, password));

		// when
		ResultActions result = mockMvc.perform(get("/customers")
			.header("Authorization", "Bearer " + tokenResponse.getAccessToken())
			.contentType(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk());
	}

}
