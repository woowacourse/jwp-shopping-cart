package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.OrderRequest;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("addOrder메서드는")
    @Nested
    class addOrderTest {

        @DisplayName("장바구니 번호에 빈값은 허용하지 않는다.")
        @ParameterizedTest
        @NullSource
        void addWithNullCartId(Long cartId) throws Exception {
            List<OrderRequest> request = List.of(new OrderRequest(cartId, 3));
            System.out.println(objectMapper.writeValueAsString(request));
            assertBadRequestFromPost("/api/members/me/orders", request, "장바구니 번호는 빈값일 수 없습니다.");
        }

        @DisplayName("장바구니 번호가 long의 범위를 넘어가는 경우는 허용하지 않는다.")
        @Test
        void maxCartId() throws Exception {
            assertBadRequestFromPost("/api/members/me/orders", "[{\"cartId\":9223372036854775808,\"quantity\":1}]", "out of range of long");
        }

        @DisplayName("수량에 1이상의 정수만 허용한다.")
        @ParameterizedTest
        @ValueSource(ints = {-1, 0})
        void negativeQuantity(int quantity) throws Exception {
            List<OrderRequest> request = List.of(new OrderRequest(1L, quantity));
            assertBadRequestFromPost("/api/members/me/orders", request, "수량은 1이상이어야 합니다.");
        }

        @DisplayName("수량이 int의 범위를 넘어가는 경우는 허용하지 않는다.")
        @Test
        void maxQuantity() throws Exception {
            assertBadRequestFromPost("/api/members/me/orders", "[{\"cartId\":1,\"quantity\":2147483648}]", "out of range of int");
        }
    }

    void assertBadRequestFromPost(String uri, String request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(post(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }

    void assertBadRequestFromPost(String uri, Object request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(post(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }

}
