package woowacourse.shoppingcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.request.UpdateCustomerDto;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static woowacourse.fixture.Fixture.TEST_EMAIL;

class CartItemItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CartItemController cartItemController;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
    }

    @Test
    @DisplayName("장바구니에 담긴 물건들을 가져온다.")
    void getCartItems() {
    }

    @Test
    @DisplayName("장바구니에 담긴 물건들을 가져온다.")
    void addCartItem() {
    }

    @Test
    @DisplayName("장바구니에 담긴 물건을 삭제한다.")
    void deleteCartItem() {
    }

    @Test
    @DisplayName("장바구니에 담긴 물건의 수량을 수정한다.")
    void updateCartItem() {

    }
}
