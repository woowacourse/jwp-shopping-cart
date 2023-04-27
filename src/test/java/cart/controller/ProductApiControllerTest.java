package cart.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.request.ProductRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductApiController.class)
class ProductApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @Nested
    @DisplayName("상품 생성(POST) :")
    class create {

        @Test
        @DisplayName("상품을 정상적으로 생성하면 201을 반환한다.")
        void create_success() throws Exception {
            ProductRequest request = new ProductRequest("name", 1000, "www.naver.com");
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/product")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isCreated())
                    .andDo(print());
        }


        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("상품의 이름이 공백이거나 null이면 400을 반환한다.")
        void create_fail_by_wrong_product_name(String wrongValue) throws Exception {
            ProductRequest request = new ProductRequest(wrongValue, 1000, "www.naver.com");
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/product")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", is("이름을 입력해주세요.")))
                    .andDo(print());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("가격이 null이면 400을 반환한다.")
        void create_fail_by_null_price(Integer wrongPrice) throws Exception {
            ProductRequest request = new ProductRequest("name", wrongPrice, "url");
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/product")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", is("가격을 입력해주세요.")))
                    .andDo(print());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("이미지 url이 공백이거나 예외를 400을 반환한다.")
        void create_fail_by_wrong_image_url(String wrongValue) throws Exception {
            ProductRequest request = new ProductRequest("name", 1000, wrongValue);
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/product")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", is("이미지 URL을 입력해주세요.")))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("상품 수정(PUT) :")
    class update {

        @Test
        @DisplayName("상품을 정상적으로 수정하면 200을 반환한다.")
        void update_success() throws Exception {
            ProductRequest request = new ProductRequest("name", 1000, "www.naver.com");
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/product/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isOk())
                    .andDo(print());
        }


        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("상품의 이름이 공백이거나 null이면 400을 반환한다.")
        void update_fail_by_wrong_product_name(String wrongValue) throws Exception {
            ProductRequest request = new ProductRequest(wrongValue, 1000, "www.naver.com");
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/product/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", is("이름을 입력해주세요.")))
                    .andDo(print());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("가격이 null이면 400을 반환한다.")
        void update_fail_by_null_price(Integer wrongPrice) throws Exception {
            ProductRequest request = new ProductRequest("name", wrongPrice, "url");
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/product/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", is("가격을 입력해주세요.")))
                    .andDo(print());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("이미지 url이 공백이거나 null이면 400을 반환한다.")
        void update_fail_by_wrong_image_url(String wrongValue) throws Exception {
            ProductRequest request = new ProductRequest("name", 1000, wrongValue);
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/product/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("message", is("이미지 URL을 입력해주세요.")))
                    .andDo(print());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("path가 null이면 400을 반환한다.")
        void update_fail_by_null_path(String wrongValue) throws Exception {
            ProductRequest request = new ProductRequest("name", 1000, "image_url");
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(put("/product/" + wrongValue)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("상품 삭제(DELETE) :")
    class delete {

        @Test
        @DisplayName("상품을 정상적으로 삭제하면 200을 반환한다.")
        void delete_success() throws Exception {
            ProductRequest request = new ProductRequest("name", 1000, "www.naver.com");
            String content = objectMapper.writeValueAsString(request);

            mockMvc.perform(delete("/product/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().isOk())
                    .andDo(print());
        }
    }
}
