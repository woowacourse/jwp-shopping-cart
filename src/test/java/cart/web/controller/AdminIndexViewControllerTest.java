package cart.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.domain.product.TestFixture;
import cart.domain.product.service.ProductService;
import cart.domain.product.service.dto.ProductDto;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@MockBean(BasicAuthorizedUserArgumentResolver.class)
@WebMvcTest({AdminViewController.class, IndexViewController.class})
class AdminIndexViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @DisplayName("루트 경로 요청시, index.html을 반환한다.")
    @Test
    void loadIndexPage() throws Exception {
        Mockito.when(productService.getAllProducts())
                .thenReturn(List.of(ProductDto.from(TestFixture.CHICKEN), ProductDto.from(TestFixture.PIZZA)));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(view().name("index"))
                .andDo(print());
    }

    @DisplayName("/admin 요청시, admin.html을 반환한다.")
    @Test
    void loadAdminPage() throws Exception {
        List<ProductDto> expectedProducts = List.of(
                ProductDto.from(TestFixture.PIZZA),
                ProductDto.from(TestFixture.CHICKEN)
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
