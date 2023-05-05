package cart.controller;

import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
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
        Product product = new Product(1L, "치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        given(productService.findAll()).willReturn(List.of(ProductResponse.from(product)));

        // when, then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("치킨")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("imageUrl", is("https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("price", is(10000)))))
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
        Member member = new Member(1L, "a@a.com", "password1");
        given(memberService.findAll()).willReturn(List.of(MemberResponse.from(member)));

        // when, then
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attribute("members", hasItem(hasProperty("email", is("a@a.com")))))
                .andExpect(model().attribute("members", hasItem(hasProperty("password", is("password1")))))
                .andExpect(view().name("settings"));
    }

    @DisplayName("'/admin'으로 GET 요청을 했을 때 admin template을 반환한다.")
    @Test
    void getAdminPage() throws Exception {
        // given
        Product product = new Product(1L, "치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 10000);
        given(productService.findAll()).willReturn(List.of(ProductResponse.from(product)));

        // when, then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("치킨")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("imageUrl", is("https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("price", is(10000)))))
                .andExpect(view().name("admin"));
    }
}