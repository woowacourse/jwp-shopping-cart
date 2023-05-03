package cart.controller;

import static cart.factory.ProductFactory.createProduct;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.User;
import cart.dto.ProductsReadResponse;
import cart.service.ProductService;
import cart.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ViewController.class)
class ViewControllerTest {

    @MockBean
    ProductService productService;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Home을 반환한다.")
    void returns_home_view() throws Exception {
        // given
        ProductsReadResponse expected = ProductsReadResponse.from(List.of(createProduct()));
        given(productService.findAll()).willReturn(expected);

        // when & then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @DisplayName("Admin을 반환한다.")
    void returns_admin_view() throws Exception {
        // given
        ProductsReadResponse expected = ProductsReadResponse.from(List.of(createProduct()));
        given(productService.findAll()).willReturn(expected);

        // when & then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @DisplayName("Settings를 반환한다.")
    void returns_settings_view() throws Exception {
        // given
        User user = new User("rosie@google.com", "password");
        User otherUser = new User("poz@wooteco.com", "1234");
        List<User> users = List.of(user, otherUser);
        BDDMockito.given(userService.findAllUser())
                        .willReturn(users);

        // when & then
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }

    @Test
    @DisplayName("Cart를 반환한다.")
    void returns_cart_view() throws Exception {
        // when & then
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andDo(print());
    }
}
