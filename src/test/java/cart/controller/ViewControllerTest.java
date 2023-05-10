package cart.controller;

import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
import cart.controller.view.ViewController;
import cart.domain.Member;
import cart.domain.Product;
import cart.service.MemberService;
import cart.service.ProductService;
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

@WebMvcTest(ViewController.class)
class ViewControllerTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("'/'로 GET 요청을 했을 때 index template을 반환한다.")
    @Test
    void getIndexPage() throws Exception {
        // given
        Long productId = 1L;
        String name = "치킨";
        String imageUrl = "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg";
        int price = 10000;
        Product product = new Product(productId, name, imageUrl, price);
        given(productService.findAll()).willReturn(List.of(ProductResponse.from(product)));

        // when, then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("id", is(productId)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is(name)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("imageUrl", is(imageUrl)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("price", is(price)))))
                .andExpect(view().name("index"));
    }

    @DisplayName("'/cart'으로 GET 요청을 했을 때 cart template을 반환한다.")
    @Test
    void getCartPage() throws Exception {
        // when, then
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }

    @DisplayName("'/settings'으로 GET 요청을 했을 때 settings template을 반환한다.")
    @Test
    void getSettingsPage() throws Exception {
        // given
        Long memberId = 1L;
        String email = "a@a.com";
        String password = "password1";
        Member member = new Member(memberId, email, password);
        given(memberService.findAll()).willReturn(List.of(MemberResponse.from(member)));

        // when, then
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attribute("members", hasItem(hasProperty("email", is(email)))))
                .andExpect(model().attribute("members", hasItem(hasProperty("password", is(password)))))
                .andExpect(view().name("settings"));
    }

    @DisplayName("'/admin'으로 GET 요청을 했을 때 admin template을 반환한다.")
    @Test
    void getAdminPage() throws Exception {
        // given
        Long productId = 1L;
        String name = "치킨";
        String imageUrl = "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg";
        int price = 10000;
        Product product = new Product(productId, name, imageUrl, price);
        given(productService.findAll()).willReturn(List.of(ProductResponse.from(product)));

        // when, then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("id", is(productId)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is(name)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("imageUrl", is(imageUrl)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("price", is(price)))))
                .andExpect(view().name("admin"));
    }
}
