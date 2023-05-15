package cart.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import cart.auth.BasicAuthorizationExtractor;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.domain.Member;
import cart.domain.Product;

@Import(BasicAuthorizationExtractor.class)
@WebMvcTest(PageController.class)
class PageControllerTest {

    @MockBean
    private ProductDao productDao;

    @MockBean
    private MemberDao memberDao;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("'/'로 GET 요청을 했을 때 index template을 반환한다.")
    @Test
    void index() throws Exception {
        // given
        Product product = new Product(1, "치킨", "image.url", 10000);
        given(productDao.findAll()).willReturn(List.of(product));

        // when, then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("치킨")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("imageUrl", is("image.url")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("price", is(10000)))))
                .andExpect(view().name("index"));
    }

    @DisplayName("/admin으로 GET 요청을 했을 때 admin template을 반환한다.")
    @Test
    void admin() throws Exception {
        // given
        Product product = new Product(1, "샐러드", "image.url", 20000);
        given(productDao.findAll()).willReturn(List.of(product));

        // when, then
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("샐러드")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("imageUrl", is("image.url")))))
                .andExpect(model().attribute("products", hasItem(hasProperty("price", is(20000)))))
                .andExpect(view().name("admin"));
    }

    @DisplayName("/settings으로 GET 요청을 했을 때 settings template을 반환한다.")
    @Test
    void settings() throws Exception {
        // given
        Member member = new Member(1L, "jeomxon@gmail.com", "password1");
        given(memberDao.findAll()).willReturn(List.of(member));

        // when, then
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attribute("members", hasItem(hasProperty("email", is("jeomxon@gmail.com")))))
                .andExpect(model().attribute("members", hasItem(hasProperty("password", is("password1")))))
                .andExpect(view().name("settings"));
    }

    @DisplayName("/cart으로 GET 요청을 했을 때 cart template을 반환한다.")
    @Test
    void cart() throws Exception {
        // when, then
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }
}
