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

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @MockBean
    private ProductDao productDao;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("/admin으로 GET 요청을 했을 때 admin template을 반환한다.")
    @Test
    void findAllProducts() throws Exception {
        // given
        Product product = new Product(1, "치킨", "image.url", 10000);
        given(productDao.findAll()).willReturn(List.of(product));

        // when, then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("치킨")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("imageUrl", is("image.url")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("price", is(10000)))))
                .andExpect(view().name("admin"));
    }
}