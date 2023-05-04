package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.dto.ModifyRequest;
import cart.dao.ProductDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductDao productDao;

    @DisplayName("POST /admin/product 요청 시")
    @Nested
    class postAdminProduct {

        @DisplayName("입력이 올바른 경우 Status OK를 반환한다.")
        @Test
        void shouldResponseStatusOkWhenRequestPostToProducts() throws Exception {
            final ModifyRequest request = new ModifyRequest("사과", 100, "domain.com");
            final String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        }

        @DisplayName("이름이 공백인 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        void shouldResponseStatusBadRequestWhenNameIsBlank(String inputName) throws Exception {
            final ModifyRequest request = new ModifyRequest(inputName, 100, "domain.super.com");
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("가격이 0 미만인 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "가격 입력 : {0}")
        @ValueSource(longs = {-1, -10000, -1000000})
        void shouldResponseStatusBadRequestWhenPriceIsUnderZero(long inputPrice) throws Exception {
            final ModifyRequest request = new ModifyRequest("사과", inputPrice, "domain.super.com");
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("이미지의 URL이 공백인 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        void shouldResponseStatusBadRequestWhenImageUrlIsBlank(String inputImageUrl) throws Exception {
            final ModifyRequest request = new ModifyRequest("사과", 100, inputImageUrl);
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("PUT /admin/product/{id} 요청 시")
    @Nested
    class putAdminProduct {

        @DisplayName("입력이 올바른 경우 Status OK를 반환한다.")
        @Test
        void shouldResponseStatusOkWhenRequestPutToProducts() throws Exception {
            final ModifyRequest request = new ModifyRequest("사과", 100, "domain.com");
            final String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        }

        @DisplayName("이름이 공백인 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        void shouldResponseStatusBadRequestWhenNameIsBlank(String inputName) throws Exception {
            final ModifyRequest request = new ModifyRequest(inputName, 100, "domain.super.com");
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("가격이 0 미만인 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "가격 입력 : {0}")
        @ValueSource(longs = {-1, -10000, -1000000})
        void shouldResponseStatusBadRequestWhenPriceIsUnderZero(long inputPrice) throws Exception {
            final ModifyRequest request = new ModifyRequest("사과", inputPrice, "domain.super.com");
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("이미지의 URL이 공백인 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        void shouldResponseStatusBadRequestWhenImageUrlIsBlank(String inputImageUrl) throws Exception {
            final ModifyRequest request = new ModifyRequest("사과", 100, inputImageUrl);
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("DELETE /admin/product/{id} 요청 시 Status OK를 반환한다.")
    @Test
    void shouldResponseStatusOkWhenRequestDeleteToProductId() throws Exception {
        mockMvc.perform(delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
