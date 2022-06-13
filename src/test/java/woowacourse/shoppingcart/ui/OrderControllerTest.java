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

public class OrderControllerTest extends ControllerTest {

    private Long customerId;

    private Long productId1;
    private Long productId2;
    private Long productId3;

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
        productId1 = productInsertUtil.insert("치킨", 20000, "https://test.jpg");
        productId2 = productInsertUtil.insert("콜라", 1500, "https://test.jpg");
        productId3 = productInsertUtil.insert("피자", 15000, "https://test.jpg");

        customerId = customerService.signUp(new CustomerRequest(email, password, nickname))
            .getId();
        token = authService.login(new TokenRequest(email, password))
            .getAccessToken();
        cartService.setItem(customerId, productId1, 2);
        cartService.setItem(customerId, productId2, 2);
        cartService.setItem(customerId, productId3, 2);
    }

    @DisplayName("장바구니의 모든 상품을 주문한다.")
    @Test
    void orderAllCartItem() throws Exception {
        // given
        ProductIdsRequest request = new ProductIdsRequest(List.of(productId1, productId2, productId3));

        // when
        ResultActions result = mockMvc.perform(post("/orders")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.totalPrice").value(73000));
        assertThat(cartService.findItemsByCustomer(customerId)).isEmpty();
    }

    @DisplayName("장바구니 상품 1개만 주문한다.")
    @Test
    void orderOneCartItem() throws Exception {
        // given
        ProductIdsRequest request = new ProductIdsRequest(List.of(productId1));

        // when
        ResultActions result = mockMvc.perform(post("/orders")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.totalPrice").value(40000));
        assertThat(cartService.findItemsByCustomer(customerId).size()).isEqualTo(2);
    }

    @DisplayName("장바구니에 없는 상품을 주문하면 예외가 발생한다.")
    @Test
    void orderNotInCart() throws Exception {
        // given
        ProductIdsRequest request = new ProductIdsRequest(List.of(productId1, productId2 + productId3));

        // when
        ResultActions result = mockMvc.perform(post("/orders")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isNotFound());
        assertThat(cartService.findItemsByCustomer(customerId).size()).isEqualTo(3);
    }
}
