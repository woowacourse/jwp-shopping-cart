package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.config.RestDocsConfig;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.fixture.TokenFixture.ACCESS_TOKEN;
import static woowacourse.fixture.TokenFixture.BEARER;

@DisplayName("장바구니 API 문서화")
@AutoConfigureRestDocs
@WebMvcTest(CartItemController.class)
@Import(RestDocsConfig.class)
class CartItemControllerTest {

    @MockBean
    private CartService cartService;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("장바구니 조회 문서화")
    @Test
    void getCartItems() throws Exception {
        Customer customer = new Customer(1L, "giron", "passwrdd1@A");
        Cart cart1 = new Cart(1L, 1L, "이름", 5000, "www.imageuirl1.com", 50);
        Cart cart2 = new Cart(2L, 2L, "이름2", 4000, "www.imageuirl2.com", 100);
        Cart cart3 = new Cart(3L, 3L, "이름3", 1000, "www.imageuirl3.com", 10);
        List<CartResponse> cartResponses = List.of(new CartResponse(cart1), new CartResponse(cart2), new CartResponse(cart3));

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(cartService.findAllByCustomerName(any())).willReturn(cartResponses);

        ResultActions results = mvc.perform(get("/api/customers/me/carts")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("carts-get",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("장바구니 식별자"),
                                fieldWithPath("[].productId").type(JsonFieldType.NUMBER).description("상품의 식별자"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("장바구니 상품의 이름"),
                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("상품 이미지 url"),
                                fieldWithPath("[].quantity").type(JsonFieldType.NUMBER).description("장바구니에 담은 개수")
                        )
                ));
    }

    @DisplayName("장바구니에 상품 추가 문서화")
    @Test
    void addCartItem() throws Exception {
        Customer customer = new Customer(1L, "giron", "passwrdd1@A");

        ProductRequest.OnlyId request = new ProductRequest.OnlyId(1L);
        Cart cart = new Cart(1L, 1L, "girona", 5000, "www.imageuirl1.com", 50);

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(cartService.addCart(any(), any())).willReturn(cart.getId());

        ResultActions results = mvc.perform(post("/api/customers/me/carts")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(request)));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("carts-add",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location의 url마지막에 cart의 식별자가 있습니다")
                        )
                ));
    }

    @DisplayName("장바구니 삭제 문서화")
    @Test
    void deleteCartItem() throws Exception {
        Customer customer = new Customer(1L, "giron", "passwrdd1@A");

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        doNothing().when(cartService).delete(customer.getUserName(), 1L);

        ResultActions results = mvc.perform(delete("/api/customers/me/carts/{cartId}", 1)
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("carts-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        )));
    }

    @DisplayName("상품 수량 변경 문서화")
    @Test
    void updateQuantity() throws Exception {
        Customer customer = new Customer(1L, "giron", "passwrdd1@A");
        CartRequest cartRequest = new CartRequest(80);

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        doNothing().when(cartService).updateQuantity(customer.getUserName(), 1L, 30);

        ResultActions results = mvc.perform(patch("/api/customers/me/carts/{cartId}", 1)
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(cartRequest)));

        results.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("carts-quantity-update",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        )));
    }
}
