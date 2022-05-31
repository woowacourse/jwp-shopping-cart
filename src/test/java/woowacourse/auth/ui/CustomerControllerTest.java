package woowacourse.auth.ui;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import woowacourse.auth.application.AuthService;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.service.CustomerService;
import woowacourse.auth.support.JwtTokenProvider;

@WebMvcTest({CustomerController.class, JwtTokenProvider.class, AuthService.class})
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CustomerService customerService;

	@DisplayName("회원가입을 한다.")
	@Test
	void signUp() throws Exception {
		// given
		CustomerRequest request = new CustomerRequest("123@gmail.com", "a1234!", "does");
		String requestJson = objectMapper.writeValueAsString(request);
		given(customerService.signUp(any(CustomerRequest.class)))
				.willReturn(new Customer(1L, "123@gmail.com", "a1234!", "does"));

		// when
		mockMvc.perform(post("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson))
			.andExpect(status().isCreated())
			.andExpect(content().json(objectMapper.writeValueAsString(
				new CustomerResponse("123@gmail.com", "does")))
			);
	}

	@DisplayName("중복된 이메일이 있으면 가입하지 못한다.")
	@Test
	void signUpDuplicatedEmail() throws Exception {
		// given
		CustomerRequest request = new CustomerRequest("123@gmail.com", "a1234!", "does");
		String requestJson = objectMapper.writeValueAsString(request);
		given(customerService.signUp(any(CustomerRequest.class)))
			.willThrow(IllegalArgumentException.class);

		// when
		mockMvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isBadRequest());
	}


	@DisplayName("이메일의 형식이 올바르지 못하면 400 반환")
	@ParameterizedTest
	@ValueSource(strings = {"123gmail.com", "123@gmailcom", "123gamilcom"})
	void emailFormatException(String email) throws Exception {
		// given
		CustomerRequest request = new CustomerRequest(email, "a1234!", "does");
		String requestJson = objectMapper.writeValueAsString(request);
		given(customerService.signUp(any(CustomerRequest.class)))
			.willThrow(IllegalArgumentException.class);

		// when
		mockMvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isBadRequest());
	}

	@DisplayName("닉네임의 형식이 올바르지 못하면 400 반환")
	@ParameterizedTest
	@ValueSource(strings = {"1", "12345678901"})
	void nicknameFormatException(String nickname) throws Exception {
		// given
		CustomerRequest request = new CustomerRequest("123@gmail.com", "aa%$5%$f!", nickname);
		String requestJson = objectMapper.writeValueAsString(request);
		given(customerService.signUp(any(CustomerRequest.class)))
			.willThrow(IllegalArgumentException.class);

		// when
		mockMvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isBadRequest());
	}

	@DisplayName("비밀번호의 형식이 올바르지 못하면 400 반환")
	@ParameterizedTest
	@ValueSource(strings = {"1234", "abasdas", "!@#!@#", "123d213", "asdasd!@#@", "123!@@##!1"})
	void passwordFormatException(String password) throws Exception {
		// given
		CustomerRequest request = new CustomerRequest("123@gmail.com", password, "does");
		String requestJson = objectMapper.writeValueAsString(request);
		given(customerService.signUp(any(CustomerRequest.class)))
			.willThrow(IllegalArgumentException.class);

		// when
		mockMvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isBadRequest());
	}
}
