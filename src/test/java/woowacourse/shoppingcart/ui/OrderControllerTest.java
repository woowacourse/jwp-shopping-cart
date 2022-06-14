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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.OrderResponse;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.request.PasswordRequest;
import woowacourse.shoppingcart.dto.request.PhoneNumberRequest;
import woowacourse.shoppingcart.dto.request.SignInRequest;
import woowacourse.shoppingcart.dto.response.TokenResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenProvider provider;

    private String accessToken;

    @BeforeEach
    void setUp() {
        customerService.create(new CustomerRequest("yhh1056", "hoho", "Abcd123!", "호호하우스",
                new PhoneNumberRequest("010", "1234", "1234")));
        TokenResponse response = customerService.signIn(new SignInRequest("yhh1056", "Abcd123!"));
        accessToken = response.getAccessToken();
    }

    @AfterEach
    void tearDown() {
        customerService.delete(Long.valueOf(provider.getPayload(accessToken)), new PasswordRequest("Abcd123!"));
    }

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrder() throws Exception {
        // given
        Long cartId = 1L;
        int quantity = 5;
        Long cartId2 = 1L;
        int quantity2 = 5;
        Long customerId = Long.valueOf(provider.getPayload(accessToken));
        List<OrderRequest> requestDtos =
                Arrays.asList(new OrderRequest(cartId, quantity),
                        new OrderRequest(cartId2, quantity2));

        Long expectedOrderId = 1L;
        when(orderService.addOrder(any(), eq(customerId)))
                .thenReturn(expectedOrderId);

        // when // then
        mockMvc.perform(post("/customers/" + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header("Authorization", "Bearer " + accessToken)
                .content(objectMapper.writeValueAsString(requestDtos)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/orders/" + expectedOrderId));
    }

    @DisplayName("사용자 이름과 주문 ID를 통해 단일 주문 내역을 조회하면, 단일 주문 내역을 받는다.")
    @Test
    void findOrder() throws Exception {

        // given
        Long customerId = Long.valueOf(provider.getPayload(accessToken));
        final Long orderId = 1L;
        final OrderResponse expected = new OrderResponse(orderId,
                Collections.singletonList(new OrderDetail(2L, 1_000, "banana", "imageUrl", 2)));

        when(orderService.findOrderById(customerId, orderId))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/customers/orders/" + orderId)
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("orderId").value(orderId))
                .andExpect(jsonPath("order[0].cost").value(1_000))
                .andExpect(jsonPath("order[0].name").value("banana"))
                .andExpect(jsonPath("order[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("order[0].quantity").value(2));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        Long customerId = Long.valueOf(provider.getPayload(accessToken));
        final List<OrderResponse> expected = Arrays.asList(
                new OrderResponse(1L, Collections.singletonList(
                        new OrderDetail(1L, 1_000, "banana", "imageUrl", 2))),
                new OrderResponse(2L, Collections.singletonList(
                        new OrderDetail(2L, 2_000, "apple", "imageUrl2", 4)))
        );

        when(orderService.findOrdersByCustomerName(customerId))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/customers/orders/")
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1L))
                .andExpect(jsonPath("$[0].order[0].cost").value(1_000))
                .andExpect(jsonPath("$[0].order[0].name").value("banana"))
                .andExpect(jsonPath("$[0].order[0].imageUrl").value("imageUrl"))
                .andExpect(jsonPath("$[0].order[0].quantity").value(2))

                .andExpect(jsonPath("$[1].orderId").value(2L))
                .andExpect(jsonPath("$[1].order[0].cost").value(2_000))
                .andExpect(jsonPath("$[1].order[0].name").value("apple"))
                .andExpect(jsonPath("$[1].order[0].imageUrl").value("imageUrl2"))
                .andExpect(jsonPath("$[1].order[0].quantity").value(4));
    }
}
