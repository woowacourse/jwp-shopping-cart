package woowacourse.shoppingcart.unit.order.ui;

import static org.hamcrest.Matchers.contains;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.order.domain.Orders;
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;
import woowacourse.shoppingcart.order.exception.notfound.NotFoundCartItemException;
import woowacourse.shoppingcart.order.exception.notfound.NotFoundOrderException;
import woowacourse.shoppingcart.unit.ControllerTest;

class OrderControllerTest extends ControllerTest {

    private static final String REQUEST_URL = "/users/me/orders";

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
                post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/users/me/orders/" + orderId));

        // docs
        perform.andDo(document("add-order-contains-all",
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
                post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNotFound());

        // docs
        perform.andDo(document("add-order-not-contains",
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

    @Test
    @DisplayName("주문 Id에 해당하는 주문을 단건 조회한다.")
    void findOrder() throws Exception {
        // given
        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final List<OrderDetail> orderDetails = List.of(
                new OrderDetail(1L, 340, "망고망고", "man.go", 2),
                new OrderDetail(4L, 1200, "오렌지", "orange.org", 7)
        );
        final Orders orders = new Orders(2L, orderDetails);
        final Long orderId = 2L;
        given(orderService.findOrderById(customer, orderId))
                .willReturn(orders);

        // when
        final ResultActions perform = mockMvc.perform(
                get("/users/me/orders/{orderId}", orderId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$..productId", contains(1, 4)))
                .andExpect(jsonPath("$..price", contains(340, 1200)))
                .andExpect(jsonPath("$..name", contains("망고망고", "오렌지")))
                .andExpect(jsonPath("$..imageUrl", contains("man.go", "orange.org")))
                .andExpect(jsonPath("$..quantity", contains(2, 7)));

        // docs
        perform.andDo(document("find-order",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                pathParameters(
                        parameterWithName("orderId").description("주문 ID")
                ),
                responseFields(
                        fieldWithPath("id").description("주문 ID"),
                        fieldWithPath("orderDetails[].productId").description("상품 ID"),
                        fieldWithPath("orderDetails[].quantity").description("상품 수량"),
                        fieldWithPath("orderDetails[].price").description("상품 가격"),
                        fieldWithPath("orderDetails[].name").description("상품 이름"),
                        fieldWithPath("orderDetails[].imageUrl").description("상품 사진 URL")
                )
        ));
    }

    @Test
    @DisplayName("주문 Id에 해당하는 주문이 존재하지 않으면 404를 반환한다.")
    void findOrder_notExistOrder_404() throws Exception {
        // given
        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final Long orderId = 2L;
        given(orderService.findOrderById(customer, orderId))
                .willThrow(new NotFoundOrderException());

        // when
        final ResultActions perform = mockMvc.perform(
                get("/users/me/orders/{orderId}", orderId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isNotFound());

        // docs
        perform.andDo(document("find-order-not-exist-order",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                pathParameters(
                        parameterWithName("orderId").description("주문 ID")
                ),
                responseFields(
                        fieldWithPath("errorCode").description("에러 코드"),
                        fieldWithPath("message").description("에러 메시지")
                )
        ));
    }

    @Test
    @DisplayName("모든 주문 목록을 조회한다.")
    void findOrders() throws Exception {
        // given
        final String accessToken = "fake-toke";
        final Customer customer = new Customer(1L, "rick", "rick@gmail.com", HASH);
        getLoginCustomerByToken(accessToken, customer);

        final List<OrderDetail> firstOrderDetails = List.of(
                new OrderDetail(1L, 340, "망고망고", "man.go", 2),
                new OrderDetail(4L, 1200, "오렌지", "orange.org", 7)
        );
        final List<OrderDetail> secondOrderDetails = List.of(
                new OrderDetail(2L, 2700, "복숭아", "pea.ch", 3)
        );
        final List<Orders> orders = List.of(
                new Orders(2L, firstOrderDetails),
                new Orders(6L, secondOrderDetails)
        );
        given(orderService.findAllOrders(customer))
                .willReturn(orders);

        // when
        final ResultActions perform = mockMvc.perform(
                get(REQUEST_URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(document("find-orders",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("토큰")
                ),
                responseFields(
                        fieldWithPath("[].id").description("주문 ID"),
                        fieldWithPath("[].orderDetails[].productId").description("상품 ID"),
                        fieldWithPath("[].orderDetails[].quantity").description("상품 수량"),
                        fieldWithPath("[].orderDetails[].price").description("상품 가격"),
                        fieldWithPath("[].orderDetails[].name").description("상품 이름"),
                        fieldWithPath("[].orderDetails[].imageUrl").description("상품 사진 URL")
                )
        ));
    }
}
