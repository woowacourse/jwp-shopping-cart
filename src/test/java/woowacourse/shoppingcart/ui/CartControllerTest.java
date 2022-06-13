package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.fixture.MemberFixture.BEARER;
import static woowacourse.helper.fixture.MemberFixture.TOKEN;
import static woowacourse.helper.restdocs.RestDocsUtils.getRequestPreprocessor;
import static woowacourse.helper.restdocs.RestDocsUtils.getResponsePreprocessor;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.shoppingcart.dto.CartQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;

public class CartControllerTest extends RestDocsTest {

    @DisplayName("카트를 조회한다.")
    @Test
    void findCartsById() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        given(cartService.findCartsById(anyLong())).willReturn(cartResponses());

        final ResultActions resultActions = mockMvc.perform(get("/api/members/me/carts")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(cartResponses())));

        // docs
        resultActions.andDo(document("carts-get",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].productId").type(NUMBER).description("제품 id"),
                        fieldWithPath("[].name").type(STRING).description("제품명"),
                        fieldWithPath("[].imageUrl").type(STRING).description("제품이미지"),
                        fieldWithPath("[].totalPrice").type(NUMBER).description("제품 총 가격"),
                        fieldWithPath("[].quantity").type(NUMBER).description("제품 양")
                )));
    }

    @DisplayName("카트에 제품을 저장한다.")
    @Test
    void addCart() throws Exception {
        final CartRequest cartRequest = new CartRequest(1L);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        given(cartService.addCart(anyLong(), any(CartRequest.class))).willReturn(1L);

        final ResultActions resultActions = mockMvc.perform(post("/api/members/me/carts")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .content(objectMapper.writeValueAsString(cartRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/members/me/carts/1"));

        resultActions.andDo(document("carts-add",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("productId").type(NUMBER).description("제품 id")
                )));
    }

    @DisplayName("카트에 제품 저장시 id가 존재하지 않아 예외를 발생한다.")
    @Test
    void addCartProductIdNull() throws Exception {
        final CartRequest cartRequest = new CartRequest(null);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        ErrorResponse response = new ErrorResponse("product id를 입력하세요.");

        mockMvc.perform(post("/api/members/me/carts")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @DisplayName("카트의 제품을 삭제한다.")
    @Test
    void deleteCart() throws Exception {
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        doNothing().when(cartService).deleteCart(anyLong(), anyLong());

        final ResultActions resultActions = mockMvc.perform(delete("/api/members/me/carts/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isNoContent());

        resultActions.andDo(document("carts-delete",
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                )));
    }

    @DisplayName("카트의 물품 개수를 업데이트한다.")
    @Test
    void updateQuantity() throws Exception {
        final CartQuantityUpdateRequest cartQuantityUpdateRequest = new CartQuantityUpdateRequest(10);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        doNothing().when(cartService).updateCartQuantity(anyLong(), anyLong(), any(CartQuantityUpdateRequest.class));

        final ResultActions resultActions = mockMvc.perform(delete("/api/members/me/carts/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .content(objectMapper.writeValueAsString(cartQuantityUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        resultActions.andDo(document("carts-update",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("quantity").type(NUMBER).description("개수")
                )));
    }


    @DisplayName("카트에 양 업데이트시 개수가 1개 미만이라 예외를 발생한다.")
    @Test
    void updateCartQuantityLowerThanMin() throws Exception {
        final CartQuantityUpdateRequest cartQuantityUpdateRequest = new CartQuantityUpdateRequest(0);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        ErrorResponse response = new ErrorResponse("장바구니 물품 개수를 1개 이상 입력해주세요.");

        mockMvc.perform(put("/api/members/me/carts/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartQuantityUpdateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    private List<CartResponse> cartResponses() {
        final CartResponse cartResponse1 = new CartResponse(1L, 1L, "김치", "image.com", 10_000, 10);
        final CartResponse cartResponse2 = new CartResponse(1L, 2L, "파김치", "image1.com", 12_000, 10);
        return List.of(cartResponse1, cartResponse2);
    }

}
