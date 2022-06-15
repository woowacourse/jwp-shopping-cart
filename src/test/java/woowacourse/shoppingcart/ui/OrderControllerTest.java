//package woowacourse.shoppingcart.ui;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.List;
//import java.util.Map;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.context.jdbc.Sql;
//import woowacourse.shoppingcart.ui.dto.request.OrderCreateRequest;
//
//@Sql({"/truncate.sql", "/auth.sql", "/product.sql", "/cart.sql"})
//public class OrderControllerTest extends ControllerTest {
//
//    @DisplayName("CREATED와 Location을 반환한다.")
//    @Test
//    void addOrder() throws Exception {
//        // given
//        final List<OrderCreateRequest> orderCreateRequests = List.of(new OrderCreateRequest(1L, 2L));
//        when(orderService.order(1L, orderCreateRequests)).thenReturn(1L);
//        when(jwtTokenProvider.getPayload("anyToken")).thenReturn(Map.of("id", 1L));
//
//        // when // then
//        mockMvc.perform(postWithTokenAndBody("/api/customers/orders", "anyToken", orderCreateRequests))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"))
//                .andExpect(header().string("Location", "/api/orders/1"));
//    }
//
//    @DisplayName("사용자 이름과 주문 ID를 통해 단일 주문 내역을 조회하면, 단일 주문 내역을 받는다.")
//    @Test
//    void findOrder() throws Exception {
////
////        // given
////        final String customerName = "pobi";
////        final Long orderId = 1L;
////        final Order expected = new Order(orderId,
////                Collections.singletonList(new OrderDetail(2L, 1_000, "banana", "imageUrl", 2)));
////
////        when(orderService.findOrderById(customerName, orderId))
////                .thenReturn(expected);
////
////        // when // then
////        mockMvc.perform(get("/api/customers/" + customerName + "/orders/" + orderId)
////                ).andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("id").value(orderId))
////                .andExpect(jsonPath("orderDetails[0].productId").value(2L))
////                .andExpect(jsonPath("orderDetails[0].price").value(1_000))
////                .andExpect(jsonPath("orderDetails[0].name").value("banana"))
////                .andExpect(jsonPath("orderDetails[0].imageUrl").value("imageUrl"))
////                .andExpect(jsonPath("orderDetails[0].quantity").value(2));
//    }
//
//    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
//    @Test
//    void findOrders() throws Exception {
////        // given
////        final String customerName = "pobi";
////        final List<Order> expected = Arrays.asList(
////                new Order(1L, Collections.singletonList(
////                        new OrderDetail(1L, 1_000, "banana", "imageUrl", 2))),
////                new Order(2L, Collections.singletonList(
////                        new OrderDetail(2L, 2_000, "apple", "imageUrl2", 4)))
////        );
////
////        when(orderService.findOrdersByCustomerName(customerName))
////                .thenReturn(expected);
////
////        // when // then
////        mockMvc.perform(get("/api/customers/" + customerName + "/orders/")
////                ).andDo(print())
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$[0].id").value(1L))
////                .andExpect(jsonPath("$[0].orderDetails[0].productId").value(1L))
////                .andExpect(jsonPath("$[0].orderDetails[0].price").value(1_000))
////                .andExpect(jsonPath("$[0].orderDetails[0].name").value("banana"))
////                .andExpect(jsonPath("$[0].orderDetails[0].imageUrl").value("imageUrl"))
////                .andExpect(jsonPath("$[0].orderDetails[0].quantity").value(2))
////
////                .andExpect(jsonPath("$[1].id").value(2L))
////                .andExpect(jsonPath("$[1].orderDetails[0].productId").value(2L))
////                .andExpect(jsonPath("$[1].orderDetails[0].price").value(2_000))
////                .andExpect(jsonPath("$[1].orderDetails[0].name").value("apple"))
////                .andExpect(jsonPath("$[1].orderDetails[0].imageUrl").value("imageUrl2"))
////                .andExpect(jsonPath("$[1].orderDetails[0].quantity").value(4));
//    }
//}
