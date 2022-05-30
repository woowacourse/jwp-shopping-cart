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
import woowacourse.auth.domain.Customer;
import woowacourse.auth.service.CustomerService;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.ui.CustomerController;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CustomerService customerService;
	@MockBean
	private AuthService authService;

	@DisplayName("회원가입을 한다.")
	@Test
	void signUp() throws Exception {
		// given
		CustomerRequest request = new CustomerRequest("123@gmail.com", "1234");
		String requestJson = objectMapper.writeValueAsString(request);
		given(customerService.signUp(any(CustomerRequest.class)))
				.willReturn(new Customer(1L, "123@gmail.com", "1234"));

		// when
		mockMvc.perform(post("/customers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson))
			.andExpect(status().isCreated());
	}

	@DisplayName("중복된 이메일이 있으면 가입하지 못한다.")
	@Test
	void signUpDuplicatedEmail() throws Exception {
		// given
		CustomerRequest request = new CustomerRequest("123@gmail.com", "1234");
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
