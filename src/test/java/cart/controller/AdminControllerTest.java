package cart.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.controller.dto.ProductResponse;
import cart.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상품 목록을 보여준다 admin.html")
    @Test
    public void showProductsList() throws Exception {
        final ProductResponse 햄버거 = new ProductResponse(1, "햄버거", "image", 1000);
        final ProductResponse 돈까스 = new ProductResponse(2, "돈까스", "image", 2000);
        final List<ProductResponse> list = List.of(햄버거, 돈까스);
        given(productService.findAll()).willReturn(list);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attribute("products", list));
    }

}
