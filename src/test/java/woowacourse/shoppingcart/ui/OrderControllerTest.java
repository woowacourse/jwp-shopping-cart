package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static woowacourse.auth.support.AuthorizationExtractor.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.acceptance.fixture.CustomerAcceptanceFixture;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.application.dto.OrderDetailResponse;
import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:schema-test.sql")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private CustomerService customerService;

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
        final String customerName = "pobi123";
        final List<OrderDetailRequest> requestDtos =
            Arrays.asList(new OrderDetailRequest(cartId, quantity), new OrderDetailRequest(cartId2, quantity2));

        final Long expectedOrderId = 1L;
        final Long customerId = customerService.createCustomer(
            CustomerAcceptanceFixture.createRequest(customerName, null));
        final String token = BEARER_TYPE + provider.createToken(customerId.toString());

        when(orderService.addOrder(any(), eq(customerId)))
            .thenReturn(expectedOrderId);

        // when // then
        mockMvc.perform(post("/api/customers/me/orders")
                .header("Authorization", token)
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
        final String customerName = "pobi123";
        final Long orderId = 1L;
        final OrderResponse expected = new OrderResponse(orderId,
            Collections.singletonList(new OrderDetailResponse(2L, 2, 2L, "banana", 1000, "http://example.com")));

        final Long customerId = customerService.createCustomer(
            CustomerAcceptanceFixture.createRequest(customerName, null));
        final String token = BEARER_TYPE + provider.createToken(customerId.toString());

        when(orderService.findOrderById(customerId, orderId))
            .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/me/orders/" + orderId)
                .header("Authorization", token)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(orderId))
            .andExpect(jsonPath("orderDetails[0].productId").value(2L))
            .andExpect(jsonPath("orderDetails[0].price").value(1_000))
            .andExpect(jsonPath("orderDetails[0].name").value("banana"))
            .andExpect(jsonPath("orderDetails[0].imageUrl").value("http://example.com"))
            .andExpect(jsonPath("orderDetails[0].quantity").value(2));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        final String customerName = "pobi123";
        final List<OrderResponse> expected = Arrays.asList(
            new OrderResponse(1L, Collections.singletonList(
                new OrderDetailResponse(1L, 2, 1L, "banana", 1000, "http://imageUrl.com"))),
            new OrderResponse(2L, Collections.singletonList(
                new OrderDetailResponse(2L, 4, 2L, "apple", 2000, "http://imageUrl2.com")))
        );

        final Long customerId = customerService.createCustomer(
            CustomerAcceptanceFixture.createRequest(customerName, null));
        final String token = BEARER_TYPE + provider.createToken(customerId.toString());

        when(orderService.findOrdersByCustomerId(customerId))
            .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/me/orders/")
                .header("Authorization", token)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].orderDetails[0].productId").value(1L))
            .andExpect(jsonPath("$[0].orderDetails[0].price").value(1_000))
            .andExpect(jsonPath("$[0].orderDetails[0].name").value("banana"))
            .andExpect(jsonPath("$[0].orderDetails[0].imageUrl").value("http://imageUrl.com"))
            .andExpect(jsonPath("$[0].orderDetails[0].quantity").value(2))

            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].orderDetails[0].productId").value(2L))
            .andExpect(jsonPath("$[1].orderDetails[0].price").value(2_000))
            .andExpect(jsonPath("$[1].orderDetails[0].name").value("apple"))
            .andExpect(jsonPath("$[1].orderDetails[0].imageUrl").value("http://imageUrl2.com"))
            .andExpect(jsonPath("$[1].orderDetails[0].quantity").value(4));
    }
}
