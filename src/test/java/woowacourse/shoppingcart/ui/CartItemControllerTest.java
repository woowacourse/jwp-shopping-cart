package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.PutCartItemRequest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CartItemController에서")
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("addCartItem메서드는")
    @Nested
    class addCartItemTest {

        @DisplayName("상품번호에 양의 정수만 허용한다.")
        @ParameterizedTest
        @ValueSource(longs = {-1, 0})
        void negativeProductId(long productId) throws Exception {
            AddCartItemRequest request = new AddCartItemRequest(productId);
            assertBadRequestFromPost("/api/members/me/carts", request, "상품번호는 양의 정수만 허용합니다.");
        }

        @DisplayName("상품번호가 long의 범위를 넘어가는 경우는 허용하지 않는다.")
        @Test
        void maxProductId() throws Exception {
            assertBadRequestFromPost("/api/members/me/carts", "{\"productId\" :  9223372036854775808}", "out of range of long");
        }
    }

    @DisplayName("putCartItem메서드는")
    @Nested
    class putCartItemTest {

        @DisplayName("수량에 양의 정수만 허용한다.")
        @ParameterizedTest
        @ValueSource(ints = {-1, 0})
        void negativeQuantity(int quantity) throws Exception {
            PutCartItemRequest request = new PutCartItemRequest(quantity);
            System.out.println(objectMapper.writeValueAsString(request));
            assertBadRequestFromPut("/api/members/me/carts/1", request, "수량은 양의 정수만 허용합니다.");
        }

        @DisplayName("수량이 int의 범위를 넘어가는 경우는 허용하지 않는다.")
        @Test
        void maxQuantity() throws Exception {
            assertBadRequestFromPut("/api/members/me/carts/1", "{\"quantity\":2147483648}", "out of range of int");
        }
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

    void assertBadRequestFromPost(String uri, String request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(post(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }

    void assertBadRequestFromPut(String uri, Object request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(put(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }

    void assertBadRequestFromPut(String uri, String request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(put(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }
}
