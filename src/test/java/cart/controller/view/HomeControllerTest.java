package cart.controller.view;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Test
    @DisplayName("메인 페이지를 보여준다.")
    void indexPage() throws Exception {
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
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", allProducts))
                .andExpect(view().name("index"));
    }
}
