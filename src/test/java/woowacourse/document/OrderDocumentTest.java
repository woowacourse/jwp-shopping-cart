package woowacourse.document;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class OrderDocumentTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private OrderService orderService;

    private final Product product1 = new Product(
            1L, "콜드 브루 몰트",
            "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001636]_20210225093600536.jpg",
            4800);
    private final Product product2 = new Product(
            2L, "바닐라 크림 콜드 브루",
            "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319040.jpg",
            4500);

    @Test
    void orderItem() throws Exception {
        when(orderService.addOrder(anyLong(), ArgumentMatchers.any(OrderRequest.class))).thenReturn(1L);

        OrderRequest orderRequest = new OrderRequest(1L, 2);
        String token = jwtTokenProvider.createToken(String.valueOf(1L));

        this.mockMvc.perform(post("/customers/orders")
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(orderRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("order/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void findAll() throws Exception {
        List<OrderResponse> orderResponses = List.of(
                OrderResponse.from(1L, product1, 3),
                OrderResponse.from(2L, product2, 15)
        );
        when(orderService.findOrdersByCustomerId(anyLong())).thenReturn(orderResponses);

        String token = jwtTokenProvider.createToken(String.valueOf(anyLong()));
        this.mockMvc.perform(get("/customers/orders")
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/find/all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void findOrder() throws Exception {
        OrderResponse orderResponse = OrderResponse.from(1L, product1, 20);
        when(orderService.findOrderById(anyLong(), anyLong())).thenReturn(orderResponse);

        String token = jwtTokenProvider.createToken(String.valueOf(1L));
        this.mockMvc.perform(get("/customers/orders/{orderId}", 1L)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("order/find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}