package cart.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import cart.domain.product.Product;
import cart.dto.ProductResponse;
import org.junit.jupiter.api.Test;

class CartSearchControllerTest extends AbstractProductControllerTest {

    @Test
    void 장바구니_조회_테스트() throws Exception {
        given(authService.isValidLogin(anyString(), anyString())).willReturn(true);
        final List<Product> products = List.of(
                new Product(1L, "odo", "url", 1),
                new Product(2L, "nunu", "url", 1)
        );
        given(cartSearchService.findByEmail(anyString())).willReturn(products);
        final List<ProductResponse> productResponses = List.of(
                new ProductResponse(1L, "odo", "url", 1),
                new ProductResponse(2L, "nunu", "url", 1)
        );
        final String result = objectMapper.writeValueAsString(productResponses);
        mockMvc.perform(get("/carts")
                        .header("Authorization", "Basic YUBhLmNvbTpwYXNzd29yZDE="))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }
}
