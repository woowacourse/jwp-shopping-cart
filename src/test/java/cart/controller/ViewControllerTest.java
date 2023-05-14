package cart.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.service.MemberService;
import cart.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ViewController.class)
class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @DisplayName("GET / - index.html 반환")
    @Test
    void shouldViewIndexWhenRequestGetToRoot() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", is("text/html;charset=UTF-8")))
                .andExpect(view().name("index"))
                .andDo(print());
    }

    @DisplayName("GET /admin - admin.html 반환")
    @Test
    void shouldViewAdminWhenRequestGetToAdmin() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", is("text/html;charset=UTF-8")))
                .andExpect(view().name("admin"))
                .andDo(print());
    }

    @DisplayName("GET /members - members.html 반환")
    @Test
    void shouldViewUsersWhenRequestGetToUsers() throws Exception {
        this.mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", is("text/html;charset=UTF-8")))
                .andExpect(view().name("members"))
                .andDo(print());
    }

    @DisplayName("GET /carts/me - cart.html 반환")
    @Test
    void shouldViewCartWhenRequestGetToCartsMe() throws Exception {
        this.mockMvc.perform(get("/carts/me"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", is("text/html;charset=UTF-8")))
                .andExpect(view().name("cart"))
                .andDo(print());
    }
}
