package cart.controller;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(AdminPageController.class)
class AdminPageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Test
    @DisplayName("관리자 페이지를 보여준다.")
    void adminPage() throws Exception {
        // given
        List<ProductDto> allProducts = List.of(
                ProductDto.fromEntity(
                        ProductEntity.builder()
                                .id(1L)
                                .name("킨더조이")
                                .price(1_000)
                                .imageUrl("imageUrl")
                                .build()
                ));

        willReturn(allProducts)
                .given(productService)
                .findAllProducts();

        // expected
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", allProducts))
                .andExpect(view().name("admin"));
    }
}
