package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.dto.ProductRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @Test
    @DisplayName("/products로 post 요청을 보내면 상태코드 201(created)을 응답한다")
    void createProduct() throws Exception {
        final String requestName = "소주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        mockMvc
            .perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isCreated())
            .andExpect(redirectedUrl("/admin"))
            .andDo(print());

    }

    @Test
    @DisplayName("/products로 잘못된 형식의 post 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void createProduct_fail() throws Exception {
        final String requestName = " ";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        mockMvc
            .perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("/products/{id}로 put 요청을 보내면 상태코드 200(OK)을 응답한다")
    void updateProduct() throws Exception {
        final String requestName = "소주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        mockMvc
            .perform(put("/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("/products로 잘못된 형식의 put 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void updateProduct_fail() throws Exception {
        final String requestName = " ";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        mockMvc
            .perform(put("/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("/products로 존재하지 않는 상품에 대해 put 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void updateProduct_fail2() throws Exception {
        final String requestName = "맥주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        doThrow(new NoSuchElementException())
            .when(productService)
            .update(any(), any());

        mockMvc
            .perform(put("/products/{id}", -1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("/products/{id}로 delete 요청을 보내면 상태코드 200(OK)을 응답한다")
    void deleteProduct() throws Exception {
        mockMvc
            .perform(delete("/products/{id}", 1L))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/products/{id}로 존재하지 않는 상품에 대해 delete 요청을 보내면 상태코드 400(BadRequest)을 응답한다")
    void deleteProduct_fail() throws Exception {
        doThrow(new NoSuchElementException())
            .when(productService)
            .delete(any());

        mockMvc
            .perform(delete("/products/{id}", -1L))
            .andExpect(status().isBadRequest());
    }
}