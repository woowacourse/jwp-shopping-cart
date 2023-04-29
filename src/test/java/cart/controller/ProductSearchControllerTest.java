package cart.controller;

import static cart.domain.product.ProductFixture.NUNU_ID_PRODUCT;
import static cart.domain.product.ProductFixture.ODO_ID_PRODUCT;
import static cart.dto.ResponseFixture.NUNU_RESPONSE;
import static cart.dto.ResponseFixture.ODO_RESPONSE;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import cart.domain.product.Product;
import cart.dto.ProductResponse;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
class ProductSearchControllerTest extends AbstractProductControllerTest {

    @Test
    void 상품_조회_테스트() throws Exception {
        final List<Product> given = List.of(NUNU_ID_PRODUCT, ODO_ID_PRODUCT);
        given(productSearchService.findAll()).willReturn(given);
        final List<ProductResponse> productResponses = List.of(NUNU_RESPONSE, ODO_RESPONSE);
        final String result = objectMapper.writeValueAsString(productResponses);

        mockMvc.perform(get("/products"))

                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }
}
