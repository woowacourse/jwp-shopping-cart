package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.fixture.MemberFixture.BEARER;
import static woowacourse.helper.fixture.MemberFixture.TOKEN;
import static woowacourse.helper.fixture.OrderFixture.getOrderDetailResponses;
import static woowacourse.helper.restdocs.RestDocsUtils.getRequestPreprocessor;
import static woowacourse.helper.restdocs.RestDocsUtils.getResponsePreprocessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.OrdersDetail;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;


public class OrderControllerTest extends RestDocsTest {

    @DisplayName("주문 한다.")
    @Test
    void addOrder() throws Exception {
        final List<OrderRequest> orderRequests = List.of(new OrderRequest(1L), new OrderRequest(2L));
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        given(orderService.addOrder(anyLong(), anyList())).willReturn(1L);

        final ResultActions resultActions = mockMvc.perform(post("/api/members/me/orders")
                                .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN)
                                .content(objectMapper.writeValueAsString(orderRequests))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/members/me/orders/1"));

        resultActions.andDo(document("orders-add",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("[].cart_id").type(NUMBER).description("카트 id")
                )));

    }

    @DisplayName("사용자 이름과 주문 ID를 통해 단일 주문 내역을 조회하면, 단일 주문 내역을 받는다.")
    @Test
    void findOrder() throws Exception {
        final OrderResponse orderResponse = new OrderResponse(1L, getOrderDetailResponses());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        given(orderService.findOrderById(anyLong(), anyLong())).willReturn(orderResponse);

        final ResultActions resultActions = mockMvc.perform(get("/api/members/me/orders/1")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(orderResponse)));

        resultActions.andDo(document("orders-get-single",
                getResponsePreprocessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("주문 id"),
                        fieldWithPath("orderDetails[].order_detail_id").type(NUMBER).description("주문 디테일 id"),
                        fieldWithPath("orderDetails[].product_id").type(NUMBER).description("제품 id"),
                        fieldWithPath("orderDetails[].name").type(STRING).description("제품 명"),
                        fieldWithPath("orderDetails[].total_price").type(NUMBER).description("제품 총 가격"),
                        fieldWithPath("orderDetails[].image_url").type(STRING).description("제품 이미지"),
                        fieldWithPath("orderDetails[].quantity").type(NUMBER).description("제품 양")
                )));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        final String customerName = "pobi";
        final List<Orders> expected = Arrays.asList(
                new Orders(1L, Collections.singletonList(
                        new OrdersDetail(1L, 1_000, "banana", "imageUrl", 2))),
                new Orders(2L, Collections.singletonList(
                        new OrdersDetail(2L, 2_000, "apple", "imageUrl2", 4)))
        );


        // when // then
        mockMvc.perform(get("/api/customers/" + customerName + "/orders/")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].productId").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("$[0].orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("$[0].orderDetails[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("$[0].orderDetails[0].quantity").value(2))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].price").value(2_000))
                .andExpect(jsonPath("$[1].orderDetails[0].name").value("apple"))
                .andExpect(jsonPath("$[1].orderDetails[0].imageUrl").value("imageUrl2"))
                .andExpect(jsonPath("$[1].orderDetails[0].quantity").value(4));
    }
}
