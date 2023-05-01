package cart.web.controller.product;

import cart.domain.product.ProductCategory;
import cart.domain.product.ProductService;
import cart.web.controller.admin.AdminViewController;
import cart.web.controller.product.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ProductViewController.class, AdminViewController.class})
class ProductViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상세 페이지를 조회한다")
    @Test
    void getProduct() throws Exception {
        // given
        final ProductResponse productResponse = new ProductResponse(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN);
        when(productService.getById(any())).thenReturn(productResponse);

        //when
        mockMvc.perform(get("/products/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }
}
