package cart.controller;

import cart.dto.RequestUpdateProductDto;
import cart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(ProductApiController.class)
class ProductApiControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper mapper;
    private Map<String, String> valueByFields;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        valueByFields = new HashMap<>();
    }

    @Test
    void 상품을_생성한다() throws Exception {
        // given
        given(productService.insert(any()))
                .willReturn(1L);

        valueByFields.put("name", "치킨");
        valueByFields.put("price", "10000");
        valueByFields.put("image", "치킨 주소");

        // expect
        mockMvc.perform(post("/admin/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(valueByFields)))
                .andExpect(status().isCreated());
    }

    @Test
    void 상품을_수정한다() throws Exception {
        // given
        given(productService.update(any(Long.class), any(RequestUpdateProductDto.class)))
                .willReturn(1);

        valueByFields.put("id", "1");
        valueByFields.put("name", "치킨");
        valueByFields.put("price", "10000");
        valueByFields.put("image", "치킨 주소");

        // expect
        mockMvc.perform(put("/admin/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(valueByFields)))
                .andExpect(status().isOk());
    }

    @Test
    void 상품을_삭제한다() throws Exception {
        // given
        given(productService.delete(any(Long.class)))
                .willReturn(1);

        // expect
        mockMvc.perform(delete("/admin/product/{id}", "1"))
                .andExpect(status().isOk());
    }
}
