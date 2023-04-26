package cart.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import cart.domain.Product;
import cart.dto.ProductResponse;
import cart.dto.ResponseFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest
class ProductSearchControllerTest extends AbstractProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 상품_조회_테스트() throws Exception {
        final List<Product> given = List.of(
                new Product(1L, "누누", "naver.com", 1),
                new Product(2L, "오도", "naver.com", 1)
        );
        given(productSearchService.find()).willReturn(given);
        final List<ProductResponse> productResponses = List.of(
                ResponseFixture.NUNU_RESPONSE,
                ResponseFixture.ODO_RESPONSE
        );
        final String result = objectMapper.writeValueAsString(productResponses);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }
}
