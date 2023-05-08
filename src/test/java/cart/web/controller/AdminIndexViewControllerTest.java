package cart.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.domain.product.TestFixture;
import cart.product.service.FindAllProductService;
import cart.product.service.dto.ProductResponseDto;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import cart.web.index.AdminViewController;
import cart.web.index.IndexViewController;
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
    private FindAllProductService findAllProductService;


    @DisplayName("루트 경로 요청시, index.html을 반환한다.")
    @Test
    void loadIndexPage() throws Exception {
        Mockito.when(findAllProductService.getAllProducts())
                .thenReturn(List.of(ProductResponseDto.from(TestFixture.CHICKEN),
                        ProductResponseDto.from(TestFixture.PIZZA)));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(view().name("index"))
                .andDo(print());
    }

    @DisplayName("/admin 요청시, admin.html을 반환한다.")
    @Test
    void loadAdminPage() throws Exception {
        final List<ProductResponseDto> expectedProducts = List.of(
                ProductResponseDto.from(TestFixture.PIZZA),
                ProductResponseDto.from(TestFixture.CHICKEN)
        );
        when(findAllProductService.getAllProducts())
                .thenReturn(expectedProducts);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(view().name("admin"))
                .andDo(print());
    }
}
