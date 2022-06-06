package woowacourse.shoppingcart.ui;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상품을 조회한다.")
    @Test
    void get() throws Exception {
        // given
        Long productId = 1L;

        // when
        when(productService.findById(1L))
                .thenReturn(new ProductResponse(1L, "apple", 1000, "http://mart/apple"));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("apple"))
                .andExpect(jsonPath("price").value(1000))
                .andExpect(jsonPath("imageUrl").value("http://mart/apple"));
    }
}
