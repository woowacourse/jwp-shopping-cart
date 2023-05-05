package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.fixture.ProductFixture.FIRST_PRODUCT;
import static cart.fixture.ProductFixture.SECOND_PRODUCT;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 상품을_조회한다() throws Exception {
        given(productService.findAll()).willReturn(List.of(FIRST_PRODUCT.RESPONSE, SECOND_PRODUCT.RESPONSE));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void 상품을_생성한다() throws Exception {
        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(FIRST_PRODUCT.REQUEST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void 상품을_수정한다() throws Exception {
        final ProductRequest productRequest = new ProductRequest("홍고", "aaaa", 10000);

        mockMvc.perform(patch("/products/1")
                        .content(objectMapper.writeValueAsString(productRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void 상품을_삭제한다() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}
