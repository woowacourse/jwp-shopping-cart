package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import woowacourse.auth.application.AuthService;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.dto.customer.CustomerRequest;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.QuantityRequest;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CartItemControllerTest {

	private Long productId;
	private String token;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductInsertUtil productInsertUtil;
	@Autowired
	private AuthService authService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void init() {
		String email = "123@gmail.com";
		String password = "a1234!";
		customerService.signUp(new CustomerRequest(email, password, "does"));

		productId = productInsertUtil.insert("치킨", 20000, "test.jpg");
		token = authService.login(new TokenRequest(email, password))
			.getAccessToken();
	}

	@DisplayName("장바구니에 상품을 추가한다.")
	@Test
	void addCartItem() throws Exception {
		// given
		QuantityRequest request = new QuantityRequest(13);

		// when
		ResultActions result = mockMvc.perform(put("/cart/products/" + productId)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		// then
		CartItemResponse response = new CartItemResponse(
			productId, "치킨", "test.jpg", 20000, 13);
		result.andExpect(status().isCreated())
			.andExpect(content().json(objectMapper.writeValueAsString(response)));
	}
}
