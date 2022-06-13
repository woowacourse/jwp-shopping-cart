package woowacourse.shoppingcart.unit.cart.ui;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.cart.dto.CartItemAdditionRequest;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;
import woowacourse.shoppingcart.cart.exception.badrequest.DuplicateCartItemException;
import woowacourse.shoppingcart.cart.exception.badrequest.InvalidQuantityException;
import woowacourse.shoppingcart.cart.exception.badrequest.NoExistCartItemException;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.exception.notfound.NotFoundProductException;
import woowacourse.shoppingcart.unit.ControllerTest;

class CartItemControllerTest extends ControllerTest {

    private static final String REQUEST_URL = "/users/me/cartItems";

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCartItem_validProduct_204() throws Exception {
        // given
        final CartItemAdditionRequest request = new CartItemAdditionRequest(1L);
        final String json = objectMapper.writeValueAsString(request);

        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        // when
        final ResultActions perform = mockMvc.perform(
                post(REQUEST_URL)
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
        final CartItemAdditionRequest request = new CartItemAdditionRequest(1L);
        final String json = objectMapper.writeValueAsString(request);

        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        given(cartService.addCart(request.getProductId(), customer))
                .willThrow(new DuplicateCartItemException());

        // when
        final ResultActions perform = mockMvc.perform(
                post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1101"))
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
        final CartItemAdditionRequest request = new CartItemAdditionRequest(1L);
        final String json = objectMapper.writeValueAsString(request);

        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        given(cartService.addCart(request.getProductId(), customer))
                .willThrow(new NotFoundProductException());

        // when
        final ResultActions perform = mockMvc.perform(
                post(REQUEST_URL)
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
    @DisplayName("장바구니에 담긴 상품 목록을 조회한다.")
    void getCart() throws Exception {
        // given
        final String accessToken = "fake-token";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final Product apple = new Product(1L, "사과", 1700, "apple.org");
        final Product carrot = new Product(2L, "당근", 800, "carrot.io");

        final Cart cart = new Cart(List.of(new CartItem(1L, apple),
                new CartItem(2L, apple),
                new CartItem(3L, apple),
                new CartItem(4L, carrot),
                new CartItem(5L, carrot),
                new CartItem(6L, carrot),
                new CartItem(7L, carrot),
                new CartItem(8L, carrot)
        ));
        given(cartService.findCartBy(customer))
                .willReturn(cart);

        // when
        final ResultActions perform = mockMvc.perform(
                get(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("cartList").isArray());

        // docs
        perform.andDo(document("get-cart-items",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("cartList[]").description("상품 목록"),
                        fieldWithPath("cartList[].id").description("상품 ID"),
                        fieldWithPath("cartList[].name").description("상품 이름"),
                        fieldWithPath("cartList[].price").description("가격"),
                        fieldWithPath("cartList[].imageUrl").description("상품 이미지 url"),
                        fieldWithPath("cartList[].quantity").description("상품 수량")
                )
        ));
    }

    @Test
    @DisplayName("장바구니에 들어있는 상품의 수량을 변경한다.")
    void changeQuantity_existProduct_200() throws Exception {
        // given
        final String accessToken = "fake-token";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final Product product = new Product(7L, "망고망고", 6500, "mangomango.go");

        final QuantityChangingRequest request = new QuantityChangingRequest(3);
        final String json = objectMapper.writeValueAsString(request);

        final CartItem cartItem = new CartItem(1L, product, request.getQuantity());
        given(cartService.changeQuantity(customer, product.getId(), request))
                .willReturn(cartItem);

        // when
        final ResultActions perform = mockMvc.perform(
                put(REQUEST_URL + "/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(product.getId()))
                .andExpect(jsonPath("quantity").value(request.getQuantity()));

        // docs
        perform.andDo(document("change-quantity-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                pathParameters(
                        parameterWithName("productId").description("상품 ID")
                ),
                requestFields(
                        fieldWithPath("quantity").description("변경하려는 수량")
                ),
                responseFields(
                        fieldWithPath("id").description("상품 ID"),
                        fieldWithPath("name").description("상품명"),
                        fieldWithPath("price").description("상품 가격"),
                        fieldWithPath("imageUrl").description("상품 사진 url"),
                        fieldWithPath("quantity").description("변경된 수량")
                )
        ));
    }

    @Test
    @DisplayName("장바구니에 없는 상품을 수정하면 400을 반환한다.")
    void changeQuantity_notExistProduct_400() throws Exception {
        // given
        final String accessToken = "fake-token";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final Product product = new Product(7L, "망고망고", 6500, "mangomango.go");

        final QuantityChangingRequest request = new QuantityChangingRequest(3);
        final String json = objectMapper.writeValueAsString(request);

        given(cartService.changeQuantity(customer, product.getId(), request))
                .willThrow(new NoExistCartItemException());

        // when
        final ResultActions perform = mockMvc.perform(
                put(REQUEST_URL + "/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1102"))
                .andExpect(jsonPath("message").value("장바구니에 상품이 존재하지 않습니다."));

        // docs
        perform.andDo(document("change-quantity-not-exist-product",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                pathParameters(
                        parameterWithName("productId").description("상품 ID")
                ),
                requestFields(
                        fieldWithPath("quantity").description("변경하려는 수량")
                ),
                responseFields(
                        fieldWithPath("errorCode").description("에러 코드"),
                        fieldWithPath("message").description("에러 메시지")
                )
        ));
    }

    @Test
    @DisplayName("변경하려는 수량이 유효하지 않으면 400을 반환한다.")
    void changeQuantity_invalidQuantity_400() throws Exception {
        // given
        final String accessToken = "fake-token";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final Product product = new Product(7L, "망고망고", 6500, "mangomango.go");

        final QuantityChangingRequest request = new QuantityChangingRequest(-1);
        final String json = objectMapper.writeValueAsString(request);

        given(cartService.changeQuantity(customer, product.getId(), request))
                .willThrow(new InvalidQuantityException());

        // when
        final ResultActions perform = mockMvc.perform(
                put(REQUEST_URL + "/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1100"))
                .andExpect(jsonPath("message").value("수량이 유효하지 않습니다."));

        // docs
        perform.andDo(document("change-quantity-invalid-quantity",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                pathParameters(
                        parameterWithName("productId").description("상품 ID")
                ),
                requestFields(
                        fieldWithPath("quantity").description("변경하려는 수량")
                ),
                responseFields(
                        fieldWithPath("errorCode").description("에러 코드"),
                        fieldWithPath("message").description("에러 메시지")
                )
        ));
    }

    @Test
    @DisplayName("장바구니에 들어있는 상품을 삭제한다.")
    void deleteCartItem_existProduct_204() throws Exception {
        // given
        final Long productId = 1L;
        final String accessToken = "fake-token";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        // when
        final ResultActions perform = mockMvc.perform(
                delete(REQUEST_URL + "/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(document("delete-cart-item",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                pathParameters(
                        parameterWithName("productId").description("상품 ID")
                )
        ));
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품을 삭제하면 400을 반환한다.")
    void deleteCartItem_notExistProduct_400() throws Exception {
        // given
        final Long productId = 1L;
        final String accessToken = "fake-token";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        willThrow(new NoExistCartItemException())
                .given(cartService)
                .deleteCartBy(customer, productId);

        // when
        final ResultActions perform = mockMvc.perform(
                delete(REQUEST_URL + "/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("1102"))
                .andExpect(jsonPath("message").value("장바구니에 상품이 존재하지 않습니다."));

        // docs
        perform.andDo(document("delete-cart-item-not-exist-item",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                pathParameters(
                        parameterWithName("productId").description("상품 ID")
                ),
                responseFields(
                        fieldWithPath("errorCode").description("에러 코드"),
                        fieldWithPath("message").description("에러 메시지")
                )
        ));
    }
}
