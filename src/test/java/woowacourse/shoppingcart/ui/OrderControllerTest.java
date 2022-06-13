package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.ProductFixture.PRODUCT_APPLE;
import static woowacourse.fixture.ProductFixture.PRODUCT_BANANA;
import static woowacourse.fixture.TokenFixture.BEARER;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    private String accessToken;
    private Long customerId;

    @BeforeEach
    void setUp() {
        CustomerRequest signUpRequest = new CustomerRequest("giron", plainBasicPassword);
        customerId = customerService.signUp(signUpRequest);

        TokenRequest tokenRequest = new TokenRequest("giron", plainBasicPassword);
        TokenResponse response = authService.login(tokenRequest);
        accessToken = response.getAccessToken();
    }

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrder() throws Exception {
        // given
        final Long cartId = 1L;
        final int quantity = 5;
        final Long cartId2 = 1L;
        final int quantity2 = 5;
        final List<OrderRequest> requestDtos =
                Arrays.asList(new OrderRequest(cartId, quantity), new OrderRequest(cartId2, quantity2));

        final Long expectedOrderId = 1L;
        when(orderService.addOrder(any(), eq(customerId)))
                .thenReturn(expectedOrderId);
        // when // then
        mockMvc.perform(post("/api/customers/me/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
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
    void findOrder() throws Exception {
        // given
        final Long orderId = 1L;
        final Orders expected = new Orders(orderId,
                Collections.singletonList(new OrderDetail(2, orderId, PRODUCT_BANANA)));

        when(orderService.findOrderById(customerId, orderId))
                .thenReturn(new OrdersResponse(expected));

        // when // then
        mockMvc.perform(get("/api/customers/me/orders/" + orderId)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(orderId))
                .andExpect(jsonPath("orderDetails[0].productId").value(1L))
                .andExpect(jsonPath("orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("orderDetails[0].imageUrl").value("woowa1.com"))
                .andExpect(jsonPath("orderDetails[0].quantity").value(2));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        final List<Orders> expected = Arrays.asList(
                new Orders(1L, Collections.singletonList(
                        new OrderDetail(2, 1L, PRODUCT_BANANA))),
                new Orders(2L, Collections.singletonList(
                        new OrderDetail(4, 1L, PRODUCT_APPLE))
                ));

        when(orderService.findOrdersByCustomerId(customerId))
                .thenReturn(expected.stream()
                        .map(OrdersResponse::new)
                        .collect(Collectors.toList()));

        // when // then
        mockMvc.perform(get("/api/customers/me/orders/")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].productId").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("$[0].orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("$[0].orderDetails[0].imageUrl").value("woowa1.com"))
                .andExpect(jsonPath("$[0].orderDetails[0].quantity").value(2))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].price").value(2_000))
                .andExpect(jsonPath("$[1].orderDetails[0].name").value("apple"))
                .andExpect(jsonPath("$[1].orderDetails[0].imageUrl").value("woowa2.com"))
                .andExpect(jsonPath("$[1].orderDetails[0].quantity").value(4));
    }
}
