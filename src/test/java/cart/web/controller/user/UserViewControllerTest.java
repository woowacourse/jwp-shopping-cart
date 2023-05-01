package cart.web.controller.user;

import cart.domain.product.ProductCategory;
import cart.domain.product.ProductService;
import cart.domain.user.UserService;
import cart.web.controller.product.dto.ProductResponse;
import cart.web.controller.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
    void index() throws Exception {
        // given
        final List<ProductResponse> productRequests = List.of(
                new ProductResponse(1L, "치킨", "chickenUrl", 20000, ProductCategory.KOREAN),
                new ProductResponse(2L, "초밥", "chobobUrl", 30000, ProductCategory.JAPANESE),
                new ProductResponse(3L, "스테이크", "steakUrl", 40000, ProductCategory.WESTERN)
        );
        when(productService.getProducts()).thenReturn(productRequests);

        // when, then
        mockMvc.perform(get("/")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @DisplayName("설정 페이지에 접근할 수 있다.")
    @Test
    void renderSettings() throws Exception {
        // given
        final List<UserResponse> userResponses = List.of(
                new UserResponse("a@a.com", "asdf"),
                new UserResponse("b@b.com", "1234")
        );

        // when
        when(userService.getUsers()).thenReturn(userResponses);

        // then
        mockMvc.perform(get("/settings").characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }
}