package woowacourse.shoppingcart.unit.order.ui;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentResponse;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;
import woowacourse.shoppingcart.order.exception.notfound.NotFoundCartItemException;
import woowacourse.shoppingcart.unit.ControllerTest;

class OrderControllerTest extends ControllerTest {

    @Test
    @DisplayName("장바구니에 담긴 상품을 주문한다.")
    void addOrder_containsAll_201() throws Exception {
        // given
        final List<OrderCreationRequest> request = List.of(
                new OrderCreationRequest(2L),
                new OrderCreationRequest(1L),
                new OrderCreationRequest(4L)
        );
        final String json = objectMapper.writeValueAsString(request);

        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final Long orderId = 3L;
        given(orderService.addOrder(request, customer))
                .willReturn(orderId);

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users/me/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/users/me/orders/" + orderId));

        // docs
        perform.andDo(document("addOrder-containsAll",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("[].productId").description("주문 하려는 상품 ID")
                )
        ));
    }

    @Test
    @DisplayName("장바구니에 담겨있지 않는 상품을 주문하면 404를 반환한다.")
    void addOrder_notContains_404() throws Exception {
        // given
        final List<OrderCreationRequest> request = List.of(
                new OrderCreationRequest(2L),
                new OrderCreationRequest(1L),
                new OrderCreationRequest(4L)
        );
        final String json = objectMapper.writeValueAsString(request);

        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        given(orderService.addOrder(request, customer))
                .willThrow(new NotFoundCartItemException());

        // when
        final ResultActions perform = mockMvc.perform(
                post("/users/me/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNotFound());

        // docs
        perform.andDo(document("addOrder-notContains",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                requestFields(
                        fieldWithPath("[].productId").description("주문 하려는 상품 ID")
                ),
                responseFields(
                        fieldWithPath("errorCode").description("에러 코드"),
                        fieldWithPath("message").description("에러 메시지")
                )
        ));
    }
}
