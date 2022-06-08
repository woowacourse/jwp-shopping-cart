package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrderDetailResponse;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

public class OrderControllerTest extends ControllerTest {

    @MockBean
    private OrderService orderService;

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrder() throws Exception {
        // given
        final Long cartId = 1L;
        final int quantity = 5;
        final Long cartId2 = 1L;
        final int quantity2 = 5;
        final List<OrderRequest> requestDtos = Arrays
                .asList(new OrderRequest(cartId, quantity), new OrderRequest(cartId2, quantity2));

        final Long expectedOrderId = 1L;
        when(orderService.addOrder(any(), any()))
                .thenReturn(expectedOrderId);

        // when // then
        mockMvc.perform(postWithToken("/api/customer/orders", TOKEN, requestDtos)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @DisplayName("토큰과 함께 주문 ID를 통해 단일 주문 내역을 조회하면, 단일 주문 내역을 받는다.")
    @Test
    void findOrder() throws Exception {
        // given
        final Long orderId = 1L;
        final int totalPrice = 2000;
        final OrdersResponse expected = new OrdersResponse(orderId, totalPrice,
                List.of(new OrderDetailResponse(2L, 2, 1_000, "banana", "imageUrl")));

        when(orderService.findOrderById(any(), any()))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(getWithToken("/api/customer" + "/orders/" + orderId, TOKEN)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(orderId))
                .andExpect(jsonPath("totalPrice").value(totalPrice))
                .andExpect(jsonPath("orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("orderDetails[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("orderDetails[0].quantity").value(2));
    }

    @DisplayName("토큰과 함께 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        final List<OrdersResponse> expected = Arrays.asList(
                new OrdersResponse(1L, 2000, List.of(new OrderDetailResponse(1L, 2, 1_000, "banana", "imageUrl"))),
                new OrdersResponse(2L, 8000, List.of(new OrderDetailResponse(2L, 4, 2_000, "apple", "imageUrl2")))
        );

        when(orderService.findOrdersByCustomerId(any()))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(getWithToken("/api/customer" + "/orders", TOKEN)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].totalPrice").value(2000))
                .andExpect(jsonPath("$[0].orderDetails[0].productId").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("$[0].orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("$[0].orderDetails[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("$[0].orderDetails[0].quantity").value(2))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].totalPrice").value(8000))
                .andExpect(jsonPath("$[1].orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].price").value(2_000))
                .andExpect(jsonPath("$[1].orderDetails[0].name").value("apple"))
                .andExpect(jsonPath("$[1].orderDetails[0].imageUrl").value("imageUrl2"))
                .andExpect(jsonPath("$[1].orderDetails[0].quantity").value(4));
    }
}
