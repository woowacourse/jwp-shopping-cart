package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.fixture.MemberFixture.BEARER;
import static woowacourse.helper.fixture.MemberFixture.TOKEN;
import static woowacourse.helper.fixture.ProductFixture.IMAGE;
import static woowacourse.helper.fixture.ProductFixture.NAME;
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
import woowacourse.auth.exception.WrongTokenException;
import woowacourse.helper.fixture.OrderFixture;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@DisplayName("주문 컨트롤러 단위테스트")
public class OrderControllerTest extends RestDocsTest {

    @DisplayName("주문 등록에 성공한다.")
    @Test
    void register() throws Exception {
        OrderRequest request = new OrderRequest(1L);
        given(orderService.addOrder(anyList(), anyLong())).willReturn(1L);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(post("/api/members/me/orders")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(request))))
                .andExpect(status().isCreated());

        //docs
        resultActions.andDo(document("order-register",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("[].cartId").type(NUMBER).description("id")
                )));
    }

    @DisplayName("주문 등록에 실패한다.")
    @Test
    void failedRegister() throws Exception {
        OrderRequest request = new OrderRequest(1L);

        doThrow(InvalidCartItemException.class).when(orderService).addOrder(anyList(), anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(post("/api/members/me/orders")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(request))))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("주문을 조회한다.")
    @Test
    void getOrder() throws Exception {
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.from(
                OrderFixture.createProduct(createProductWithId(1L, NAME, PRICE, IMAGE), 5)
        );
        OrdersResponse ordersResponse = new OrdersResponse(1L, List.of(orderDetailResponse));
        given(orderService.findOrderById(anyLong(), anyLong())).willReturn(ordersResponse);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(get("/api/members/me/orders/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(ordersResponse)));

        //docs
        resultActions.andDo(document("order-get",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                responseFields(
                        fieldWithPath("orderId").type(NUMBER).description("id"),
                        subsectionWithPath("orderDetails").type(ARRAY).description("상세")
                )));
    }

    @DisplayName("주문을 조회에 실패한다.")
    @Test
    void failedGetOrder() throws Exception {
        doThrow(InvalidOrderException.class).when(orderService).findOrderById(anyLong(), anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(get("/api/members/me/orders/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("주문 목록을 조회한다.")
    @Test
    void getOrders() throws Exception {
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.from(
                OrderFixture.createProduct(createProductWithId(1L, NAME, PRICE, IMAGE), 5)
        );
        OrdersResponse ordersResponse = new OrdersResponse(1L, List.of(orderDetailResponse));
        given(orderService.findOrdersByMemberId(anyLong())).willReturn(List.of(ordersResponse));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        ResultActions resultActions = mockMvc.perform(get("/api/members/me/orders")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(ordersResponse))));

        //docs
        resultActions.andDo(document("order-list-get",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                responseFields(
                        fieldWithPath("[].orderId").type(NUMBER).description("id"),
                        subsectionWithPath("[].orderDetails").type(ARRAY).description("상세")
                )));
    }

    @DisplayName("주문목록을 조회에 실패한다.")
    @Test
    void failedGetOrders() throws Exception {
        doThrow(WrongTokenException.class).when(orderService).findOrdersByMemberId(anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(false);

        mockMvc.perform(get("/api/members/me/orders")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
