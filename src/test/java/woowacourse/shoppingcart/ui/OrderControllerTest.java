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

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.ui.AuthenticationContext;
import woowacourse.auth.ui.LoginInterceptor;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;
import woowacourse.shoppingcart.dto.order.OrdersResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginInterceptor loginInterceptor;

    @MockBean
    private AuthenticationContext authenticationContext;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        when(loginInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrder() throws Exception {
        // given
        final Long cartId = 1L;
        final Long cartId2 = 1L;
        final String customerName = "pobi";
        final List<OrderSaveRequest> requestDtos =
                Arrays.asList(new OrderSaveRequest(cartId), new OrderSaveRequest(cartId2));

        final Long expectedOrderId = 1L;
        when(authenticationContext.getPrincipal()).thenReturn(customerName);
        when(orderService.addOrder(any(), eq(customerName)))
                .thenReturn(expectedOrderId);

        // when // then
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(requestDtos))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location",
                        "/api/" + customerName + "/orders/" + expectedOrderId));
    }

    @DisplayName("사용자 이름과 주문 ID를 통해 단일 주문 내역을 조회하면, 단일 주문 내역을 받는다.")
    @Test
    void findOrder() throws Exception {

        // given
        final String customerName = "pobi";
        final Long orderId = 1L;
        final OrderResponse expected = OrderResponse.from(new Orders(orderId,
                Collections.singletonList(new OrderDetail(1L, 2L, "banana", 1_000, 2, "imageUrl"))));

        when(authenticationContext.getPrincipal()).thenReturn(customerName);
        when(orderService.findOrderById(customerName, orderId))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/orders/" + orderId)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("order.id").value(orderId))
                .andExpect(jsonPath("order.orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("order.orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("order.orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("order.orderDetails[0].imageURL").value("imageUrl"))
                .andExpect(jsonPath("order.orderDetails[0].quantity").value(2));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        final String customerName = "pobi";
        final OrdersResponse expected = OrdersResponse.from(Arrays.asList(
                new Orders(1L, Collections.singletonList(
                        new OrderDetail(1L, 1L, "banana", 1_000, 2, "imageUrl"))),
                new Orders(2L, Collections.singletonList(
                        new OrderDetail(2L, 2L, "apple", 2_000, 4, "imageUrl2")))
        ));

        when(authenticationContext.getPrincipal()).thenReturn(customerName);
        when(orderService.findOrdersByCustomerName(customerName))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/orders/")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("orders[0].id").value(1L))
                .andExpect(jsonPath("orders[0].orderDetails[0].productId").value(1L))
                .andExpect(jsonPath("orders[0].orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("orders[0].orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("orders[0].orderDetails[0].imageURL").value("imageUrl"))
                .andExpect(jsonPath("orders[0].orderDetails[0].quantity").value(2))

                .andExpect(jsonPath("orders[1].id").value(2L))
                .andExpect(jsonPath("orders[1].orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("orders[1].orderDetails[0].price").value(2_000))
                .andExpect(jsonPath("orders[1].orderDetails[0].name").value("apple"))
                .andExpect(jsonPath("orders[1].orderDetails[0].imageURL").value("imageUrl2"))
                .andExpect(jsonPath("orders[1].orderDetails[0].quantity").value(4));
    }
}
