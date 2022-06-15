package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.fixture.MemberFixture.BEARER;
import static woowacourse.helper.fixture.MemberFixture.TOKEN;
import static woowacourse.helper.fixture.ProductFixture.IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProductWithId;
import static woowacourse.helper.restdocs.RestDocsUtils.getRequestPreprocessor;
import static woowacourse.helper.restdocs.RestDocsUtils.getResponsePreprocessor;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.helper.fixture.ProductFixture;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;

@DisplayName("카트 컨트롤러 단위테스트")
class CartItemControllerTest extends RestDocsTest {

    @DisplayName("장바구니 물품 등록에 성공한다.")
    @Test
    void register() throws Exception {
        AddCartItemRequest request = new AddCartItemRequest(1L);

        given(cartService.addCart(anyLong(), anyLong())).willReturn(1L);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(post("/api/members/me/carts")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        //docs
        resultActions.andDo(document("cart-register",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("productId").type(NUMBER).description("id")
                )));
    }

    @DisplayName("장바구니 물품 등록에 실패한다.")
    @Test
    void failedRegister() throws Exception {
        AddCartItemRequest request = new AddCartItemRequest(1L);

        doThrow(InvalidProductException.class).when(cartService).addCart(anyLong(), anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(post("/api/members/me/carts")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("장바구니 물품 조회한다.")
    @Test
    void getCartItems() throws Exception {
        CartItemResponse cartItemResponse = CartItemResponse.from(
                new Cart(1L, createProductWithId(1L, ProductFixture.NAME, PRICE, IMAGE), 5)
        );
        given(cartService.findCartsByMemberId(anyLong())).willReturn(List.of(cartItemResponse));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(get("/api/members/me/carts")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(cartItemResponse))));

        //docs
        resultActions.andDo(document("cart-list-get",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].productId").type(NUMBER).description("상품id"),
                        fieldWithPath("[].name").type(STRING).description("이름"),
                        fieldWithPath("[].price").type(NUMBER).description("가격"),
                        fieldWithPath("[].imageUrl").type(STRING).description("이미지"),
                        fieldWithPath("[].totalPrice").type(NUMBER).description("총액"),
                        fieldWithPath("[].quantity").type(NUMBER).description("수량")
                )));
    }

    @DisplayName("장바구니 물품 조회에 실패한다.")
    @Test
    void failedGetCartItems() throws Exception {
        doThrow(InvalidCartItemException.class).when(cartService).findCartsByMemberId(anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(get("/api/members/me/carts")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("장바구니 물품 수량을 수정한다.")
    @Test
    void updateQuantity() throws Exception {
        UpdateQuantityRequest updateQuantityRequest = new UpdateQuantityRequest(5);

        doNothing().when(cartService).updateCartItemQuantity(anyLong(), any(UpdateQuantityRequest.class));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(put("/api/members/me/carts/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateQuantityRequest)))
                .andExpect(status().isOk());

        // docs
        resultActions.andDo(document("cart-quantity-update",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("quantity").description("수량")
                )));

    }

    @DisplayName("장바구니 물품 수량을 수정에 실패한다.")
    @Test
    void failedUpdateQuantity() throws Exception {
        UpdateQuantityRequest updateQuantityRequest = new UpdateQuantityRequest(5);
        doThrow(InvalidCartItemException.class)
                .when(cartService).updateCartItemQuantity(anyLong(), any(UpdateQuantityRequest.class));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(put("/api/members/me/carts/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateQuantityRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("장바구니 물품을 삭제한다.")
    @Test
    void deleteCartItem() throws Exception {
        doNothing().when(cartService).deleteCart(anyLong(), anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(delete("/api/members/me/carts/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isNoContent());

        resultActions.andDo(document("cart-delete",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                )));
    }

    @DisplayName("장바구니 물품을 삭제에 실패한다.")
    @Test
    void failedDeleteCartItem() throws Exception {
        doThrow(InvalidCartItemException.class).when(cartService).deleteCart(anyLong(), anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(delete("/api/members/me/carts/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isBadRequest());
    }
}
