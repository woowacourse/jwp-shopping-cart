package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.PutCartItemRequest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        @Test
        void negativeProductId() throws Exception {
            AddCartItemRequest request = new AddCartItemRequest(-1);
            assertBadRequestFromPost("/api/members/me/carts", request, "상품번호는 양의 정수만 허용합니다.");
        }
    }

    @DisplayName("putCartItem메서드는")
    @Nested
    class putCartItemTest {

        @DisplayName("수량에 양의 정수만 허용한다.")
        @Test
        void negativeQuantity() throws Exception {
            PutCartItemRequest request = new PutCartItemRequest(-1);
            assertBadRequestFromPut("/api/members/me/carts/1", request, "수량은 양의 정수만 허용합니다.");
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

    void assertBadRequestFromPut(String uri, Object request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(put(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }
}
