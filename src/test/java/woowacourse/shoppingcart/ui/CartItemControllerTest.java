package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import woowacourse.auth.application.AuthService;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.dto.customer.CustomerRequest;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.auth.ui.ControllerTest;
import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.ProductIdsRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.QuantityRequest;

class CartItemControllerTest extends ControllerTest {

	private Long customerId;
	private Long productId;
	private String token;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductInsertUtil productInsertUtil;
	@Autowired
	private AuthService authService;
	@Autowired
	private CartService cartService;

	@BeforeEach
	void init() {
		customerId = customerService.signUp(new CustomerRequest(email, password, "does"))
			.getId();

		productId = productInsertUtil.insert("치킨", 20000, "https://test.jpg");
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
			productId, "치킨", "https://test.jpg", 20000, 13);
		result.andExpect(status().isCreated())
			.andExpect(content().json(objectMapper.writeValueAsString(response)));
	}

	@DisplayName("이미 있는 장바구니 상품의 수량을 변경한다.")
	@Test
	void updateCartItem() throws Exception {
		// given
		cartService.setItem(customerId, productId, 13);
		int updatedQuantity = 20;

		// when
		QuantityRequest request = new QuantityRequest(updatedQuantity);
		ResultActions result = mockMvc.perform(put("/cart/products/" + productId)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		// then
		CartItemResponse response = new CartItemResponse(
			productId, "치킨", "https://test.jpg", 20000, updatedQuantity);
		result.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(response)));
	}

	@DisplayName("로그인 사용자의 장바구니 목록을 조회한다.")
	@Test
	void getItems() throws Exception {
		// given
		cartService.setItem(customerId, productId, 2);
		Long productId2 = productInsertUtil.insert("콜라", 1500, "https://test.jpg");
		cartService.setItem(customerId, productId2, 3);

		// when
		ResultActions result = mockMvc.perform(get("/cart")
			.header("Authorization", "Bearer " + token));

		// then
		CartItemResponse response1 = new CartItemResponse(
			productId, "치킨", "https://test.jpg", 20000, 2);
		CartItemResponse response2 = new CartItemResponse(
			productId2, "콜라", "https://test.jpg", 1500, 3);
		result.andExpect(status().isOk())
			.andExpect(content().json(
				objectMapper.writeValueAsString(List.of(response1, response2))));
	}

	@DisplayName("장바구니 상품들을 삭제한다.")
	@Test
	void deleteItems() throws Exception {
		// given
		cartService.setItem(customerId, productId, 2);
		Long productId2 = productInsertUtil.insert("콜라", 1500, "https://test.jpg");
		cartService.setItem(customerId, productId2, 3);

		// when
		String request = objectMapper.writeValueAsString(
			new ProductIdsRequest(List.of(productId, productId2)));

		ResultActions result = mockMvc.perform(delete("/cart")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		// then
		result.andExpect(status().isNoContent());
		assertThat(cartService.findItemsByCustomer(customerId)).isEmpty();
	}

	@DisplayName("장바구니에 없는 상품을 삭제하면 404 예외가 발생한다.")
	@Test
	void deleteItemsFail() throws Exception {
		// given
		cartService.setItem(customerId, productId, 2);
		Long productId2 = productInsertUtil.insert("콜라", 1500, "https://test.jpg");
		cartService.setItem(customerId, productId2, 3);

		// when
		String request = objectMapper.writeValueAsString(
			new ProductIdsRequest(List.of(productId, productId2, productId + productId2)));

		ResultActions result = mockMvc.perform(delete("/cart")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		// then
		result.andExpect(status().isNotFound());
		assertThat(cartService.findItemsByCustomer(customerId).size()).isEqualTo(2);
	}
}
