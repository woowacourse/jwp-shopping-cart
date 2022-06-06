package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.OrderRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static woowacourse.fixture.PasswordFixture.ORIGIN_USER_1_PASSWORD;
import static woowacourse.fixture.PasswordFixture.RAW_BASIC_PASSWORD;
import static woowacourse.fixture.TokenFixture.ACCESS_TOKEN;
import static woowacourse.fixture.TokenFixture.BEARER;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
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

    private Customer customer;
    @BeforeEach
    void setUp(){
         customer = new Customer("pobi", new Password(ORIGIN_USER_1_PASSWORD));
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
    void findOrder() throws Exception {

        // given
        final Long orderId = 1L;
        final Orders expected = new Orders(orderId,
                Collections.singletonList(new OrderDetail(2L, 1_000, "banana", "imageUrl", 2)));

        given(authService.getAuthenticatedCustomer(ACCESS_TOKEN)).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        when(orderService.findOrderById(customer.getUserName(), orderId)).thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/me/orders/" + orderId)
                        .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(orderId))
                .andExpect(jsonPath("orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("orderDetails[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("orderDetails[0].quantity").value(2));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        final List<Orders> expected = Arrays.asList(
                new Orders(1L, Collections.singletonList(
                        new OrderDetail(1L, 1_000, "banana", "imageUrl", 2))),
                new Orders(2L, Collections.singletonList(
                        new OrderDetail(2L, 2_000, "apple", "imageUrl2", 4)))
        );

        given(authService.getAuthenticatedCustomer(ACCESS_TOKEN)).willReturn(customer);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        when(orderService.findOrdersByCustomerName(customer.getUserName())).thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/me/orders")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN)
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
