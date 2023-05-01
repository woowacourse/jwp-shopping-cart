package cart.web.controller.admin;

import cart.domain.product.ProductCategory;
import cart.domain.product.ProductService;
import cart.web.controller.product.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminViewController.class)
class AdminViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() throws Exception {
        // given
        final List<ProductResponse> productRequests = List.of(
                new ProductResponse(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                new ProductResponse(2L, "초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                new ProductResponse(3L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
        );
        when(productService.getProducts()).thenReturn(productRequests);

        // when, then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

}