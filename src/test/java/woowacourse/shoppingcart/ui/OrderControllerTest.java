package woowacourse.shoppingcart.ui;

import static Fixture.CustomerFixtures.*;
import static Fixture.ProductFixtures.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider provider;

    @MockBean
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrder() throws Exception {
        // given
        customerService.save(YAHO_SAVE_REQUEST);
        Long cartId = 1L;
        int quantity = 5;
        Long cartId2 = 1L;
        int quantity2 = 5;
        LoginCustomer loginCustomer = new LoginCustomer(YAHO_USERNAME);
        List<OrderRequest> requestDtos =
                Arrays.asList(new OrderRequest(cartId, quantity), new OrderRequest(cartId2, quantity2));

        Long expectedOrderId = 1L;
        String token = "Bearer " + provider.createToken(YAHO_USERNAME);

        when(orderService.addOrder(any(), eq(loginCustomer)))
                .thenReturn(expectedOrderId);

        // when // then
        mockMvc.perform(post("/api/customers/me/orders")
                        .header(HttpHeaders.AUTHORIZATION, token)
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
        LoginCustomer loginCustomer = new LoginCustomer("pobi123");
        Long orderId = 1L;
        Orders expected = new Orders(orderId,
                Collections.singletonList(new OrderDetail(CHICKEN, 2)));

        String token = "Bearer " + provider.createToken(YAHO_USERNAME);

        when(orderService.findOrderById(loginCustomer, orderId))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/me/orders/" + orderId)
                        .header(HttpHeaders.AUTHORIZATION, token)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        LoginCustomer loginCustomer = new LoginCustomer("pobi123");
        List<Orders> expected = Arrays.asList(
                new Orders(1L, Collections.singletonList(new OrderDetail(CHICKEN, 2))),
                new Orders(2L, Collections.singletonList(new OrderDetail(BEER, 4)))
        );

        String token = "Bearer " + provider.createToken(YAHO_USERNAME);

        when(orderService.findOrdersByCustomerName(loginCustomer))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/customers/me/orders/")
                        .header(HttpHeaders.AUTHORIZATION, token)
                ).andDo(print())
                .andExpect(status().isOk());
    }
}
