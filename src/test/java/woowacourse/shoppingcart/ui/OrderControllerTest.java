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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.OrderedProduct;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.dto.order.OrderCreateRequest;
import woowacourse.shoppingcart.dto.order.OrderResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @DisplayName("CREATED와 Location을 반환한다.")
    @Test
    void addOrder() throws Exception {
        // given
        final String customerName = "pobi";

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(List.of(1L, 2L));

        final Long expectedOrderId = 1L;
        when(orderService.addOrder(any(), eq(customerName)))
                .thenReturn(expectedOrderId);

        // when // then
        mockMvc.perform(post("/api/myorders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(orderCreateRequest))
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
        final ThumbnailImage thumbnailImage = new ThumbnailImage("url", "alt");
        final OrderResponse expected = new OrderResponse(orderId,
                Collections.singletonList(new OrderedProduct(2L, 2, 100, "banana", thumbnailImage)));

        when(orderService.findOrderById(customerName, orderId))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/myorders/" + orderId)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(orderId))
                .andExpect(jsonPath("orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("orderDetails[0].image.url").value(thumbnailImage.getUrl()))
                .andExpect(jsonPath("orderDetails[0].image.alt").value(thumbnailImage.getAlt()))
                .andExpect(jsonPath("orderDetails[0].quantity").value(2));
    }

    @DisplayName("사용자 이름을 통해 주문 내역 목록을 조회하면, 주문 내역 목록을 받는다.")
    @Test
    void findOrders() throws Exception {
        // given
        final String customerName = "pobi";
        final ThumbnailImage firstThumbnailImage = new ThumbnailImage("url", "alt");
        final ThumbnailImage secondThumbnailImage = new ThumbnailImage("url", "alt");

        final List<OrderResponse> expected = Arrays.asList(
                new OrderResponse(1L, Collections.singletonList(
                        new OrderedProduct(1L, 2, 1_000, "banana", firstThumbnailImage))),
                new OrderResponse(2L, Collections.singletonList(
                        new OrderedProduct(2L, 4, 2_000, "apple", secondThumbnailImage)))
        );

        when(orderService.findOrdersByCustomerEmail(customerName))
                .thenReturn(expected);

        // when // then
        mockMvc.perform(get("/api/myorders")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].productId").value(1L))
                .andExpect(jsonPath("$[0].orderDetails[0].price").value(1_000))
                .andExpect(jsonPath("$[0].orderDetails[0].name").value("banana"))
                .andExpect(jsonPath("$[0].orderDetails[0].image.url").value(firstThumbnailImage.getUrl()))
                .andExpect(jsonPath("$[0].orderDetails[0].image.alt").value(firstThumbnailImage.getAlt()))
                .andExpect(jsonPath("$[0].orderDetails[0].quantity").value(2))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].productId").value(2L))
                .andExpect(jsonPath("$[1].orderDetails[0].price").value(2_000))
                .andExpect(jsonPath("$[1].orderDetails[0].name").value("apple"))
                .andExpect(jsonPath("$[0].orderDetails[0].image.url").value(secondThumbnailImage.getUrl()))
                .andExpect(jsonPath("$[0].orderDetails[0].image.alt").value(secondThumbnailImage.getAlt()))
                .andExpect(jsonPath("$[1].orderDetails[0].quantity").value(4));
    }
}
