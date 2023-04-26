package cart.controller;

import cart.dao.ProductDao;
import cart.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockBean
    private ProductDao productDao;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("'/'로 GET 요청을 했을 때 index template을 반환한다.")
    @Test
    void findAllProducts() throws Exception {
        // given
        Product product = new Product(1, "치킨", "image.url", 10000);
        given(productDao.findAll()).willReturn(List.of(product));

        // when, then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", List.of(product)))
                .andExpect(view().name("index"));
    }
}