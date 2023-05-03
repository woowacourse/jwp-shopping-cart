package cart.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.auth.LoginArgumentResolver;
import cart.controller.web.AdminController;
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

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginArgumentResolver loginArgumentResolver;

    @MockBean
    private ProductService productService;

    @DisplayName("GET /admin")
    @Test
    void getAdmin() throws Exception {
        List<Product> products = List.of(
                new Product((long) 1, "피자", 1000, "http://pizza"),
                new Product((long) 2, "햄버거", 2000, "http://hamburger"));
        given(productService.findAll()).willReturn(products);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(view().name("admin"))
                .andExpect(model().attribute("products", instanceOf(ProductsResponse.class)))
                .andExpect(model().attribute("products", hasProperty("products",
                        allOf(
                                hasItem(
                                        allOf(
                                                hasProperty("name", is("피자")),
                                                hasProperty("price", is(1000)),
                                                hasProperty("imageUrl", is("http://pizza"))
                                        )
                                ),
                                hasItem(
                                        allOf(
                                                hasProperty("name", is("햄버거")),
                                                hasProperty("price", is(2000)),
                                                hasProperty("imageUrl", is("http://hamburger"))
                                        )
                                )
                        ))));
    }
}
