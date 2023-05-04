package cart.controller;

import cart.auth.AuthArgumentResolver;
import cart.dto.request.ProductRequestDto;
import cart.exception.ProductNotFoundException;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthArgumentResolver authArgumentResolver;

    @Test
    @DisplayName("상품 생성 성공")
    void create_success() throws Exception {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("test", "image.jpg", 100);
        final String requestBody = objectMapper.writeValueAsString(requestDto);
        given(productService.create(requestDto))
                .willReturn(1);

        //expect
        mockMvc.perform(post("/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string("Location", "/admin"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("상품 생성 실패 - 모든 값 입력 안함")
    void create_fail_not_all_argument_input() throws Exception {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto(null, "image.jpg", 100);
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        //expect
        mockMvc.perform(post("/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 생성 실패 - 가격이 음수")
    void create_fail_negative_price() throws Exception {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("ditoo", "image.jpg", -100);
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        //expect
        mockMvc.perform(post("/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수정 성공")
    void update_success() throws Exception {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("ditoo", "image.jpg", 100);
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        //expect
        mockMvc.perform(patch("/products/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 수정 실패 - 모든 값 입력 안함")
    void update_not_all_argument_input() throws Exception {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto(null, "image.jpg", 100);
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        //expect
        mockMvc.perform(patch("/products/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수정 실패 - 가격이 음수")
    void update_fail_negative_price() throws Exception {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("ditoo", "image.jpg", -100);
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        //expect
        mockMvc.perform(patch("/products/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수정 실패 - 없는 상품 id")
    void update_fail_product_not_found() throws Exception {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("ditoo", "image.jpg", 100);
        final String requestBody = objectMapper.writeValueAsString(requestDto);
        doThrow(ProductNotFoundException.class)
                .when(productService)
                .update(any(), anyInt());

        //expect
        mockMvc.perform(patch("/products/0")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void delete_success() throws Exception {
        //expect
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 삭제 실패 - 없는 상품 id")
    void delete_fail_product_not_found() throws Exception {
        //given
        doThrow(ProductNotFoundException.class)
                .when(productService)
                .delete(0);

        //expect
        mockMvc.perform(delete("/products/0"))
                .andExpect(status().isNotFound());
    }
}
