package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.auth.LoginArgumentResolver;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.MembersResponse;
import cart.dto.ProductsResponse;
import cart.service.MemberService;
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

@WebMvcTest(WebViewController.class)
class WebViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginArgumentResolver loginArgumentResolver;

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("GET /")
    void renderMainPage() throws Exception {
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

    @Test
    @DisplayName("GET /admin")
    void renderAdminPage() throws Exception {
        List<Product> products = List.of(
                new Product((long) 1, "피자", 1000, "http://pizza"),
                new Product((long) 2, "햄버거", 2000, "http://hamburger"));
        given(productService.findAll()).willReturn(products);

        MvcResult result = mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(view().name("admin"))
                .andReturn();

        Object response = result.getModelAndView().getModel().get("products");
        assertThat(response)
                .isInstanceOf(ProductsResponse.class)
                .usingRecursiveComparison()
                .isEqualTo(ProductsResponse.of(products));
    }

    @Test
    @DisplayName("GET /settings")
    void renderSettingPage() throws Exception {
        List<Member> members = List.of(
                new Member((long) 1, "a@a.com", "abc1", "이오"),
                new Member((long) 2, "b@b.com", "abc2", "애쉬")
        );
        given(memberService.findAll()).willReturn(members);

        MvcResult result = mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(view().name("settings"))
                .andReturn();

        Object response = result.getModelAndView().getModel().get("members");
        assertThat(response)
                .isInstanceOf(MembersResponse.class)
                .usingRecursiveComparison()
                .isEqualTo(MembersResponse.of(members));
    }

    @Test
    @DisplayName("GET /cart")
    void renderCartPage() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(view().name("cart"));
    }
}
