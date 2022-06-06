package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import woowacourse.auth.ui.ControllerTest;
import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;

class ProductControllerTest extends ControllerTest {

	private Long productId1;
	private Product product1;
	private Product product2;

	@Autowired
	private ProductInsertUtil productInsertUtil;

	@BeforeEach
	void init() {
		productId1 = productInsertUtil.insert("치킨", 10000, "https://example.com/chicken.jpg");
		Long productId2 = productInsertUtil.insert("맥주", 20000, "https://example.com/beer.jpg");
		product1 = new Product(productId1, "치킨", 10000, "https://example.com/chicken.jpg");
		product2 = new Product(productId2, "맥주", 20000, "https://example.com/beer.jpg");
	}

	@DisplayName("상품 하나를 조회한다.")
	@Test
	void getProduct() throws Exception {
		// when
		ResultActions result = mockMvc.perform(get("/products/" + productId1));

		// then
		result.andExpect(status().isOk());
		result.andExpect(content().json(objectMapper.writeValueAsString(ProductResponse.from(product1))));
	}

	@DisplayName("없는 id로 상품을 조회하면 404 에러가 발생한다.")
	@Test
	void getProductNotFound() throws Exception {
		// when
		ResultActions result = mockMvc.perform(get("/products/" + productId1 + 2));

		// then
		result.andExpect(status().isNotFound());
	}

	@DisplayName("전체 상품을 조회한다.")
	@Test
	void getProducts() throws Exception {
		// when
		ResultActions result = mockMvc.perform(get("/products"));

		// then
		result.andExpect(status().isOk());
		result.andExpect(content().json(objectMapper.writeValueAsString(
			List.of(ProductResponse.from(product1), ProductResponse.from(product2))
		)));
	}
}
