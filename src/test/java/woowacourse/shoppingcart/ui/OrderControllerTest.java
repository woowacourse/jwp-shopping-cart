package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.config.RestDocsConfig;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static woowacourse.fixture.PasswordFixture.ORIGIN_USER_1_PASSWORD;
import static woowacourse.fixture.TokenFixture.ACCESS_TOKEN;
import static woowacourse.fixture.TokenFixture.BEARER;

@DisplayName("주문 API 문서화")
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfig.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private OrderDao orderDao;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("pobi", new Password(ORIGIN_USER_1_PASSWORD));
    }

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrderTest() throws Exception {
        // given
        final Long cartId = 1L;
        final int quantity = 5;
        final Long cartId2 = 1L;
        final int quantity2 = 5;

        final List<OrderRequest> requestDtos =
                Arrays.asList(new OrderRequest(cartId, quantity), new OrderRequest(cartId2, quantity2));

        final Long expectedOrderId = 1L;

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(authService.getAuthenticatedCustomer(ACCESS_TOKEN)).willReturn(customer);
        when(orderService.addOrder(any(), eq(customer.getUserName()))).thenReturn(expectedOrderId);

        // when // then
        mockMvc.perform(post("/api/customers/me/orders")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(requestDtos))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location",
                        "/api/customers/me/orders/" + expectedOrderId));
    }

    @DisplayName("사용자 이름과 주문 ID를 통해 단일 주문 내역을 조회하면, 단일 주문 내역을 받는다.")
    @Test
    void findOrderTest() throws Exception {

        // given
        final Long orderId = 1L;
        final Orders expected = new Orders(orderId,
                Collections.singletonList(new OrderDetail(2L, 1_000, "banana", "imageUrl", 2)));

        given(authService.getAuthenticatedCustomer(ACCESS_TOKEN)).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        when(orderService.findById(customer.getUserName(), orderId)).thenReturn(new OrdersResponse(expected));

        // when // then
        mockMvc.perform(get("/api/customers/me/orders/" + orderId)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(orderId))
                .andExpect(jsonPath("orderDetailDtos[0].productId").value(2L))
                .andExpect(jsonPath("orderDetailDtos[0].price").value(1_000))
                .andExpect(jsonPath("orderDetailDtos[0].name").value("banana"))
                .andExpect(jsonPath("orderDetailDtos[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("orderDetailDtos[0].quantity").value(2));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrdersTest() throws Exception {
        // given
        final List<Orders> orders = Arrays.asList(
                new Orders(1L, Collections.singletonList(
                        new OrderDetail(1L, 1_000, "banana", "imageUrl", 2))),
                new Orders(2L, Collections.singletonList(
                        new OrderDetail(2L, 2_000, "apple", "imageUrl2", 4)))
        );
        List<OrdersResponse> expected = orders.stream()
                .map(OrdersResponse::new)
                .collect(Collectors.toList());

        given(authService.getAuthenticatedCustomer(ACCESS_TOKEN)).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        when(orderService.findByCustomerName(customer.getUserName())).thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/me/orders")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].orderDetailDtos[0].productId").value(1L))
                .andExpect(jsonPath("$[0].orderDetailDtos[0].price").value(1_000))
                .andExpect(jsonPath("$[0].orderDetailDtos[0].name").value("banana"))
                .andExpect(jsonPath("$[0].orderDetailDtos[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("$[0].orderDetailDtos[0].quantity").value(2))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].orderDetailDtos[0].productId").value(2L))
                .andExpect(jsonPath("$[1].orderDetailDtos[0].price").value(2_000))
                .andExpect(jsonPath("$[1].orderDetailDtos[0].name").value("apple"))
                .andExpect(jsonPath("$[1].orderDetailDtos[0].imageUrl").value("imageUrl2"))
                .andExpect(jsonPath("$[1].orderDetailDtos[0].quantity").value(4));
    }

    @DisplayName("주문 추가 문서화")
    @Test
    void addOrder() throws Exception {
        // given
        Customer customer = new Customer(1L, "giron", "passwrdd1@A");
        List<OrderRequest> orderRequests = List.of(new OrderRequest(1L, 50), new OrderRequest(2L, 30));

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(orderService.addOrder(any(), any())).willReturn(1L);

        ResultActions results = mockMvc.perform(post("/api/customers/me/orders")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(orderRequests)));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("orders-add",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location의 url마지막에 cart의 식별자가 있습니다")
                        )
                ));
    }

    @DisplayName("주문 단건 조회 문서화")
    @Test
    void findOrder() throws Exception {
        // given
        Customer customer = new Customer(1L, "giron", "passwrdd1@A");
        Orders orders = new Orders(1L, Collections.singletonList(
                new OrderDetail(1L, 1_000, "banana", "imageUrl", 2)));

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(orderService.findById(any(), any())).willReturn(new OrdersResponse(orders));

        ResultActions results = mockMvc.perform(get("/api/customers/me/orders/{orderId}", 1)
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("orders-get-one",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("주문 식별자"),
                                fieldWithPath("orderDetailDtos[].productId").type(JsonFieldType.NUMBER).description("상품의 식별자"),
                                fieldWithPath("orderDetailDtos[].quantity").type(JsonFieldType.NUMBER).description("상품의 수량"),
                                fieldWithPath("orderDetailDtos[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("orderDetailDtos[].name").type(JsonFieldType.STRING).description("상품의 이름"),
                                fieldWithPath("orderDetailDtos[].imageUrl").type(JsonFieldType.STRING).description("상품 이미지 url")
                        )
                ));
    }

    @DisplayName("주문 전체 조회 문서화")
    @Test
    void findOrders() throws Exception {
        // given
        Customer customer = new Customer(1L, "giron", "passwrdd1@A");
        Orders orders1 = new Orders(1L, Collections.singletonList(
                new OrderDetail(1L, 1_000, "banana", "www.imageUr123l.com", 2)));
        Orders orders2 = new Orders(2L, Collections.singletonList(
                new OrderDetail(2L, 5_000, "apple", "www.imageUrl", 50)));

        List<Orders> orders = List.of(orders1, orders2);
        List<OrdersResponse> ordersResponses = orders.stream()
                .map(OrdersResponse::new).collect(Collectors.toList());

        given(authService.getAuthenticatedCustomer(any())).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(orderService.findByCustomerName(any())).willReturn(ordersResponses);

        ResultActions results = mockMvc.perform(get("/api/customers/me/orders")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("orders-get",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 뒤에 accessToken이 들어있습니다")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("주문 식별자"),
                                fieldWithPath("[].orderDetailDtos[].productId").type(JsonFieldType.NUMBER).description("상품의 식별자"),
                                fieldWithPath("[].orderDetailDtos[].quantity").type(JsonFieldType.NUMBER).description("상품의 수량"),
                                fieldWithPath("[].orderDetailDtos[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("[].orderDetailDtos[].name").type(JsonFieldType.STRING).description("상품의 이름"),
                                fieldWithPath("[].orderDetailDtos[].imageUrl").type(JsonFieldType.STRING).description("상품 이미지 url")
                        )
                ));

    }
}
