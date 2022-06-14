package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.config.AuthenticationPrincipalArgumentResolver;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrder() throws Exception {
        // given
        final Long cartId = 1L;
        final int quantity = 5;
        final Long cartId2 = 1L;
        final int quantity2 = 5;
        final Long customerId = 1L;
        final List<OrderRequest> requestDtos =
                Arrays.asList(new OrderRequest(cartId, quantity), new OrderRequest(cartId2, quantity2));

        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull(),
                (ModelAndViewContainer) notNull(),
                (NativeWebRequest) notNull(),
                (WebDataBinderFactory) notNull()))
                .thenReturn(customerId);
        when(orderService.addOrder(any(), any()))
                .thenReturn(1L);

        // when // then
        mockMvc.perform(post("/customers/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(requestDtos))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location",
                        "/orders/" + 1L));
    }

    @DisplayName("사용자 이름과 주문 ID를 통해 단일 주문 내역을 조회하면, 단일 주문 내역을 받는다.")
    @Test
    void findOrder() throws Exception {

        // given
        final Long customerId = 1L;
        final Long orderId = 1L;
        final Orders expected = new Orders(orderId,
                Collections.singletonList(new OrderDetail(2L, 1_000, "banana", "imageUrl", 2)));
        // when
        when(authenticationPrincipalArgumentResolver.supportsParameter((MethodParameter) notNull()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(
                (MethodParameter) notNull(),
                (ModelAndViewContainer) notNull(),
                (NativeWebRequest) notNull(),
                (WebDataBinderFactory) notNull()))
                .thenReturn(customerId);
        when(orderService.findOrderById(customerId, orderId))
                .thenReturn(expected);
        // then
        mockMvc.perform(get("/customers/orders/" + orderId)
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

        when(orderService.findOrdersByCustomerId(any()))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/customers/orders/")
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
