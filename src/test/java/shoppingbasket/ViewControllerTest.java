package shoppingbasket;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import shoppingbasket.auth.BasicAuthorizationExtractor;
import shoppingbasket.cart.service.CartService;
import shoppingbasket.member.service.MemberService;
import shoppingbasket.product.entity.ProductEntity;
import shoppingbasket.product.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class ViewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private CartService cartService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private BasicAuthorizationExtractor basicAuthorizationExtractor;

    @Test
    void indexTest() throws Exception {
        when(productService.getProducts()).thenReturn(List.of(
                new ProductEntity(1, "name1", 1000,"image1"),
                new ProductEntity(2, "name2", 2000,"image2")
        ));

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("index"));
    }

    @Test
    void adminTest() throws Exception {
        when(productService.getProducts()).thenReturn(List.of(
                new ProductEntity(1, "name1", 1000,"image1"),
                new ProductEntity(2, "name2", 2000,"image2")
        ));

        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("admin"));
    }
}
