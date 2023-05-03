package cart.web.controller.admin;

import cart.domain.TestFixture;
import cart.domain.product.ProductService;
import cart.domain.product.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminViewController.class)
class AdminViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("/admin 요청시, admin.html을 반환한다.")
    @Test
    void loadAdminPage() throws Exception {
        List<ProductDto> expectedProducts = List.of(
                new ProductDto(TestFixture.PIZZA),
                new ProductDto(TestFixture.CHICKEN)
        );
        when(productService.getAllProducts())
                .thenReturn(expectedProducts);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(view().name("admin"))
                .andDo(print());
    }

}
