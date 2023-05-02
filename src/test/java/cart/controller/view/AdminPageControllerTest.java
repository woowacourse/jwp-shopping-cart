package cart.controller.view;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.controller.MockBasicAuthProviderConfig;
import cart.dto.product.ProductDto;
import cart.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminPageController.class)
@Import(MockBasicAuthProviderConfig.class)
class AdminPageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Test
    @DisplayName("/admin으로 이동하면 관리자 페이지로 이동해야 한다.")
    void adminPage_success() throws Exception {
        // given
        given(productService.findAllProducts())
                .willReturn(List.of(new ProductDto(1L, "글렌피딕", 100_000, "image.png")));

        // expect
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("admin"));
    }

}