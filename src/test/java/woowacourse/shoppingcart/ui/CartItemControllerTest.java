package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.dto.CartItemResponse;

@SpringBootTest
@AutoConfigureMockMvc
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CartItemService cartItemService;

    @DisplayName("사용자의 장바구니 목록을 조회한다.")
    @Test
    void getCartItems() throws Exception {
        // given
        TokenRequest request = new TokenRequest(1L);

        // when
        when(cartItemService.findCartsById(any()))
                .thenReturn(List.of(
                        new CartItemResponse(1L, 1L, "apple", "http://mart/apple", 1000, 3),
                        new CartItemResponse(2L, 2L, "peach", "http://mart/peach", 2000, 5),
                        new CartItemResponse(3L, 3L, "banana", "http://mart/banana", 1500, 7)
                ));

        // then
        String token = jwtTokenProvider.createToken(String.valueOf(request.getId()));
        mockMvc.perform(get("/auth/customer/cartItems")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\":1,\"productId\":1,\"name\":\"apple\",\"imageUrl\":\"http://mart/apple\",\"price\":1000,\"quantity\":3},"
                                + "{\"id\":2,\"productId\":2,\"name\":\"peach\",\"imageUrl\":\"http://mart/peach\",\"price\":2000,\"quantity\":5},"
                                + "{\"id\":3,\"productId\":3,\"name\":\"banana\",\"imageUrl\":\"http://mart/banana\",\"price\":1500,\"quantity\":7}]"
                ));
    }
}
