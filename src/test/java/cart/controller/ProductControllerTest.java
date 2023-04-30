package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductDao productDao;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("/products으로 POST 요청이 정상적으로 작동한다.")
    @Test
    void createProduct() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);

        // when, then
        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
    }

    @DisplayName("/products/{productId}으로 PUT 요청이 정상적으로 작동한다.")
    @Test
    void modifyProduct() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);

        // when, then
        mockMvc.perform(put("/products/{productId}", "1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk());
    }

    @DisplayName("/products/{productId}으로 DELETE 요청이 정상적으로 작동한다.")
    @Test
    void removeProduct() throws Exception {
        // when, then
        mockMvc.perform(delete("/products/{productId}", "1"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("API요청 시 이름이 공백이 들어온 경우 400")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "     "})
    void validateRequestName(String inputName) throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest(inputName, "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);

        // when, then
        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("API요청 시 상품 가격이 범위를 벗어난 경우 400")
    @ParameterizedTest
    @ValueSource(ints = {-1, 100_000_001})
    void validateRequestPrice(int inputPrice) throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", inputPrice);

        // when, then
        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("API요청 시 이미지URL에 공백이 들어온 경우 400")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "     "})
    void validateRequestImageUrl(String inputImageUrl) throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("치킨", inputImageUrl, 10000);

        // when, then
        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest());
    }
}