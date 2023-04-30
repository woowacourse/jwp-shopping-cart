package cart.controller.view;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.Product;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("HomeController 는")
@WebMvcTest(HomeController.class)
@MockBean(MemberDao.class)
class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductDao productDao;

    @Test
    void 상품목록을_보여준다() throws Exception {
        Product product1 = new Product("말랑", "https://mallang.com", 1000);
        Product product2 = new Product("채채", "https://chaechae.com", 2000);
        final List<Product> products = List.of(product1, product2);

        given(productDao.findAll()).willReturn(products);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("products", products));
    }
}
