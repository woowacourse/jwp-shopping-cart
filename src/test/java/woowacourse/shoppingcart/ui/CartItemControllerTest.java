package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.config.AuthenticationPrincipalArgumentResolver;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.DeleteCartRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartService cartService;
    @MockBean
    private AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    private Cart cartItem1;
    private Cart cartItem2;

    @BeforeEach
    void setUp() {
        Product product1 = new Product(1L, "치킨", 10_000, "http://example.com/chicken.jpg");
        Product product2 = new Product(2L, "맥주", 20_000, "http://example.com/beer.jpg");
        cartItem1 = new Cart(1L, product1, 10);
        cartItem2 = new Cart(2L, product2, 5);
    }

    @DisplayName("장바구니 상품 담기에 성공하면 상태 코드 201과 URI 정보를 반환한다.")
    @Test
    void addCartItem() throws Exception {
        // given
        Long customerId = 1L;
        Long productId = 1L;
        int quantity = 10;
        CartRequest cartRequest = new CartRequest(productId, quantity);
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull(),
                (ModelAndViewContainer) notNull(),
                (NativeWebRequest) notNull(),
                (WebDataBinderFactory) notNull()))
                .thenReturn(customerId);
        // when
        when(cartService.addCart(customerId, productId, quantity))
                .thenReturn(1L);
        ResultActions perform = mockMvc.perform(post("/customers/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartRequest)));
        // then
        perform.andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated());
    }

    @DisplayName("장바구니 상품 목록 조회 시 상태 코드 200과 장바구니 목록을 반환한다.")
    @Test
    void getCartItems() throws Exception {
        // given
        Long customerId = 1L;
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull(),
                (ModelAndViewContainer) notNull(),
                (NativeWebRequest) notNull(),
                (WebDataBinderFactory) notNull()))
                .thenReturn(customerId);
        // when
        when(cartService.findCartsByCustomerId(customerId))
                .thenReturn(List.of(cartItem1, cartItem2));
        ResultActions perform = mockMvc.perform(get("/customers/carts"));
        // then
        perform.andDo(print())
                .andExpect(jsonPath("$.carts[0].productId").value(cartItem1.getProductId()))
                .andExpect(jsonPath("$.carts[0].name").value(cartItem1.getName()))
                .andExpect(jsonPath("$.carts[0].quantity").value(cartItem1.getQuantity()))
                .andExpect(jsonPath("$.carts[1].productId").value(cartItem2.getProductId()))
                .andExpect(jsonPath("$.carts[1].name").value(cartItem2.getName()))
                .andExpect(jsonPath("$.carts[1].quantity").value(cartItem2.getQuantity()))
                .andExpect(status().isOk());
    }

    @DisplayName("장바구니 상품 삭제 시 상태 코드 204를 반환한다.")
    @Test
    void deleteCartItem() throws Exception {
        // given
        Long customerId = 1L;
        DeleteCartRequest deleteCartRequest = new DeleteCartRequest(
                List.of(cartItem1.getProductId(), cartItem2.getProductId()));
        List<Long> productIds = List.of(cartItem1.getProductId(), cartItem2.getProductId());

        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull(),
                (ModelAndViewContainer) notNull(),
                (NativeWebRequest) notNull(),
                (WebDataBinderFactory) notNull()))
                .thenReturn(customerId);
        // when
        doNothing().when(cartService).deleteCart(customerId, productIds);
        ResultActions perform = mockMvc.perform(delete("/customers/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteCartRequest)));
        // then
        perform.andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("장바구니 상품 수량 수정 시 상태 코드 200을 반환한다.")
    @Test
    void updateCartItem() throws Exception {
        // given
        Long customerId = 1L;
        CartRequest cartRequest = new CartRequest(cartItem2.getProductId(), 7);
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull(),
                (ModelAndViewContainer) notNull(),
                (NativeWebRequest) notNull(),
                (WebDataBinderFactory) notNull()))
                .thenReturn(customerId);
        // when
        doNothing().when(cartService).updateCart(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        ResultActions perform = mockMvc.perform(patch("/customers/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartRequest)));
        // then
        perform.andDo(print())
                .andExpect(status().isOk());
    }
}
