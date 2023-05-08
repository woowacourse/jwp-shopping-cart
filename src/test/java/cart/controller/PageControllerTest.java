package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@WebMvcTest(PageController.class)
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("/로 get 요청을 보내면 index.html 파일을 응답한다")
    void loadHome() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("/admin로 get 요청을 보내면 admin.html 파일을 응답한다")
    void loadAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin"));
    }

    @Test
    @DisplayName("/cart로 get 요청을 보내면 cart.html 파일을 응답한다")
    void loadCart() throws Exception {
        mockMvc.perform(get("/cart"))
            .andExpect(status().isOk())
            .andExpect(view().name("cart"));
    }

    @Test
    @DisplayName("/settings로 get 요청을 보내면 settings.html 파일을 응답한다")
    void loadMembers() throws Exception {
        mockMvc.perform(get("/settings"))
            .andExpect(status().isOk())
            .andExpect(view().name("settings"));
    }
}