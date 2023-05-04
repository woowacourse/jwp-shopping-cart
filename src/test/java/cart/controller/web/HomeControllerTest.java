package cart.controller.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.auth.LoginArgumentResolver;
import cart.domain.product.Product;
import cart.dto.ProductsResponse;
import cart.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginArgumentResolver loginArgumentResolver;

    @MockBean
    private ProductService productService;

    @DisplayName("GET /")
    @Test
    void getHome() throws Exception {
        List<Product> products = List.of(
                new Product((long) 1, "피자", 1000, "http://pizza"),
                new Product((long) 2, "햄버거", 2000, "http://hamburger"));
        given(productService.findAll()).willReturn(products);

        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(view().name("index"))
                .andReturn();

        Object response = result.getModelAndView().getModel().get("products");
        assertThat(response)
                .isInstanceOf(ProductsResponse.class)
                .usingRecursiveComparison()
                .isEqualTo(ProductsResponse.of(products));

    }
}
