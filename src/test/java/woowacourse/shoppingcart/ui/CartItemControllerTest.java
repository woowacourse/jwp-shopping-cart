package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.dto.CartItemIdRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityResponse;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;
import woowacourse.shoppingcart.dto.TokenRequest;

@WebMvcTest(CartItemController.class)
@Import(JwtTokenProvider.class)
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        when(cartItemService.findCartItemsByCustomerId(any()))
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

    @DisplayName("장바구니에 새로운 물품을 추가한다.")
    @Test
    void addCartItems() throws Exception {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        List<ProductIdRequest> productIdRequests =
                List.of(new ProductIdRequest(1L), new ProductIdRequest(2L), new ProductIdRequest(3L));

        // when
        when(cartItemService.addCartItems(any(), any()))
                .thenReturn(List.of(
                        new CartItemQuantityResponse(1L, 3),
                        new CartItemQuantityResponse(2L, 5),
                        new CartItemQuantityResponse(3L, 7)
                ));

        // then
        String token = jwtTokenProvider.createToken(String.valueOf(tokenRequest.getId()));
        mockMvc.perform(post("/auth/customer/cartItems")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(productIdRequests)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\":1,\"quantity\":3},"
                                + "{\"id\":2,\"quantity\":5},"
                                + "{\"id\":3,\"quantity\":7}]"
                ));
    }

    @DisplayName("장바구니 속 물품의 정보를 수정한다.")
    @Test
    void updateCartItem() throws Exception {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        CartItemQuantityRequest cartItemQuantityRequest = new CartItemQuantityRequest(1L, 4);

        // when
        when(cartItemService.updateCartItem(any(), any()))
                .thenReturn(new CartItemQuantityResponse(1L, 4));

        // then
        String token = jwtTokenProvider.createToken(String.valueOf(tokenRequest.getId()));
        mockMvc.perform(patch("/auth/customer/cartItems")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(cartItemQuantityRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("quantity").value(4));
    }

    @DisplayName("장바구니 물품들을 제거한다.")
    @Test
    void delete() throws Exception {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        List<CartItemIdRequest> cartItemIdRequests = List.of(new CartItemIdRequest(2L), new CartItemIdRequest(3L));

        // when then
        String token = jwtTokenProvider.createToken(String.valueOf(tokenRequest.getId()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/auth/customer/cartItems")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(cartItemIdRequests)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
