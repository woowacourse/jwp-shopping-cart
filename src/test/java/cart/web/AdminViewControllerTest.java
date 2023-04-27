package cart.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.domain.product.CartService;
import cart.domain.product.TestFixture;
import cart.domain.product.dto.ProductDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminViewController.class)
class AdminViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @DisplayName("/admin 요청시, admin.html을 반환한다.")
    @Test
    void loadAdminPage() throws Exception {
        List<ProductDto> expectedProducts = List.of(
                ProductDto.from(TestFixture.PIZZA),
                ProductDto.from(TestFixture.CHICKEN)
        );
        when(cartService.getAllProducts())
                .thenReturn(expectedProducts);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(view().name("admin"))
                .andDo(print());
    }
}
