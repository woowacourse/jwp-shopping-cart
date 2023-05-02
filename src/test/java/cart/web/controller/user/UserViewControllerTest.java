package cart.web.controller.user;

import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.product.ProductService;
import cart.domain.user.User;
import cart.domain.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserViewController.class)
class UserViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    @DisplayName("메인 페이지를 조회한다")
    @Test
    void renderIndex() throws Exception {
        // given
        final List<Product> products = List.of(
                new Product(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                new Product(2L, "초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                new Product(3L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
        );
        when(productService.getProducts()).thenReturn(products);

        // when, then
        mockMvc.perform(get("/")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @DisplayName("설정 페이지에 접근할 수 있다.")
    @Test
    void renderSettings() throws Exception {
        // given
        final List<User> users = List.of(
                new User("a@a.com", "asdf"),
                new User("b@b.com", "qwer")
        );

        // when
        when(userService.getUsers()).thenReturn(users);

        // then
        mockMvc.perform(get("/settings").characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }
}