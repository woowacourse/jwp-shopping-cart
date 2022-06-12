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
import woowacourse.shoppingcart.ui.dto.ProductRequest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String name = "초콜렛";
    private final int price = 1_000;
    private final String imageUrl = "www.test.com";

    @DisplayName("상품 등록 테스트")
    @Nested
    class AddProductTest {

        @DisplayName("이름은 Null 값은 허용하지 않는다.")
        @Test
        void addWithNullName() throws Exception {
            ProductRequest request = new ProductRequest(null, price, imageUrl);
            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("상품 이름은 빈 값일 수 없습니다.")));
        }

        @DisplayName("이름은 빈 값을 허용하지 않는다.")
        @Test
        void addWithBlankName() throws Exception {
            ProductRequest request = new ProductRequest(" ", price, imageUrl);
            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("상품 이름은 빈 값일 수 없습니다.")));
        }

        @DisplayName("가격은 음수를 허용하지 않는다.")
        @Test
        void addWithNegativePrice() throws Exception {
            ProductRequest request = new ProductRequest(name, -1000, imageUrl);
            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("상품 가격은 0이하의 수가 될 수 없습니다.")));
        }

        @DisplayName("가격은 0원을 허용하지 않는다.")
        @Test
        void addWithZeroPrice() throws Exception {
            ProductRequest request = new ProductRequest(name, 0, imageUrl);
            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("상품 가격은 0이하의 수가 될 수 없습니다.")));
        }

        @DisplayName("이미지 Url은 Null 값은 허용하지 않는다.")
        @Test
        void addWithNullImageUrl() throws Exception {
            ProductRequest request = new ProductRequest(name, price, null);
            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("이미지 Url은 빈 값일 수 없습니다.")));
        }

        @DisplayName("이미지 Url은 빈 값을 허용하지 않는다.")
        @Test
        void addWithBlankImageUrl() throws Exception {
            ProductRequest request = new ProductRequest(name, price, " ");
            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("이미지 Url은 빈 값일 수 없습니다.")));
        }
    }
}
