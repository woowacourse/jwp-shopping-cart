package woowacourse.shoppingcart.unit.cart.ui;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentResponse;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.dto.CartItemAdditionRequest;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.notfound.NotFoundProductException;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.unit.ControllerTest;

class CartItemControllerTest extends ControllerTest {

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCartItem_validProduct_204() throws Exception {
        // given
        CartItemAdditionRequest request = new CartItemAdditionRequest(1L);
        final String json = objectMapper.writeValueAsString(request);

        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users/me/carts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(document("add-cart-item",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("productId").description("상품 ID")
                )
        ));
    }

    @Test
    @DisplayName("이미 추가한 상품을 추가하면 400을 반환한다.")
    void addCartItem_alreadyAddedItem_400() throws Exception {
        // given
        CartItemAdditionRequest request = new CartItemAdditionRequest(1L);
        final String json = objectMapper.writeValueAsString(request);

        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        given(cartService.addCart(request.getProductId(), customer))
                .willThrow(new DuplicateCartItemException());

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users/me/carts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1003"))
                .andExpect(jsonPath("message").value("중복된 상품입니다."));

        // docs
        perform.andDo(document("add-cart-item-duplicate-product",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("productId").description("상품 ID")
                )
        ));
    }

    @Test
    @DisplayName("존재하지 않는 상품을 추가하면 400을 반환한다.")
    void addCartItem_notExistProduct_400() throws Exception {
        // given
        CartItemAdditionRequest request = new CartItemAdditionRequest(1L);
        final String json = objectMapper.writeValueAsString(request);

        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        given(cartService.addCart(request.getProductId(), customer))
                .willThrow(new NotFoundProductException());

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users/me/carts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value("2000"))
                .andExpect(jsonPath("message").value("상품이 존재하지 않습니다."));

        // docs
        perform.andDo(document("add-cart-item-not-exist-product",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("productId").description("상품 ID")
                )
        ));
    }

    @Test
    @DisplayName("장바구니에의 상품 목록을 조회한다.")
    void getCartItems() throws Exception {
        // given
        String accessToken = "fake-token";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final Product apple = new Product(1L, "사과", 1700, "apple.org");
        final Product carrot = new Product(2L, "당근", 800, "carrot.io");

        final List<Cart> carts = List.of(
                new Cart(1L, apple),
                new Cart(2L, apple),
                new Cart(3L, apple),
                new Cart(4L, carrot),
                new Cart(5L, carrot),
                new Cart(6L, carrot),
                new Cart(7L, carrot),
                new Cart(8L, carrot)
        );
        given(cartService.findCartsByCustomerName(customer.getNickname()))
                .willReturn(carts);

        // when
        final ResultActions perform = mockMvc.perform(
                get("/users/me/carts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("products").isArray());

        // docs
        perform.andDo(document("get-cart-items",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("products[]").description("상품 목록"),
                        fieldWithPath("products[].id").description("상품 ID"),
                        fieldWithPath("products[].name").description("상품 이름"),
                        fieldWithPath("products[].price").description("가격"),
                        fieldWithPath("products[].imageUrl").description("상품 이미지 url"),
                        fieldWithPath("products[].quantity").description("상품 수량")
                )
        ));
    }
}
