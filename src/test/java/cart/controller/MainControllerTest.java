package cart.controller;

import cart.auth.AuthArgumentResolver;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import cart.dto.CartItemResponseDto;
import cart.service.CartService;
import cart.service.ProductService;
import cart.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;

    @MockBean
    private CartService cartService;

    @MockBean
    private AuthArgumentResolver authArgumentResolver;

    @Test
    @DisplayName("root directory 로 요청을 보내면 홈 html 화면을 보내준다")
    void rootPage() throws Exception {
        // given
        List<ProductEntity> allProducts = new ArrayList<>();
        given(productService.findAll())
                .willReturn(allProducts);

        // expect
        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", allProducts))
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("'/cart' directory 로 요청을 보내면 cart html 화면을 보내준다")
    void cart() throws Exception {
        // given
        final int userId = 1;
        List<CartItemResponseDto> cartProducts = new ArrayList<>();
        given(cartService.getProductsInCart(userId))
                .willReturn(cartProducts);

        // expect
        mockMvc.perform(get("/cart"))
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("'/settings' directory 로 요청을 보내면 settings html 화면을 보내준다")
    void settings() throws Exception {
        // given
        List<UserEntity> allUsers = new ArrayList<>();
        given(userService.findAll())
                .willReturn(allUsers);

        // expect
        mockMvc.perform(get("/settings"))
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attribute("members", allUsers))
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("'/admin' directory 로 요청을 보내면 admin html 화면을 보내준다")
    void admin() throws Exception {
        // given
        List<ProductEntity> allProducts = new ArrayList<>();
        given(productService.findAll())
                .willReturn(allProducts);

        // expect
        mockMvc.perform(get("/admin"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", allProducts))
                .andExpect(status().isOk());
    }
}
