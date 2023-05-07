package cart.presentation.controller;

import cart.business.service.MemberService;
import cart.business.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TemplateController.class)
class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productCRUDApplication;
    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("/ 로 GET 요청을 보낼 수 있다")
    void test_home() throws Exception {
        // given
        given(productCRUDApplication.readAll()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/"))

                // then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    @DisplayName("/admin 으로 GET 요청을 보낼 수 있다")
    void test_admin() throws Exception {
        // given
        given(productCRUDApplication.readAll()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/admin"))

                // then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin"));
    }

    @Test
    @DisplayName("/settings 으로 GET 요청을 보낼 수 있다")
    void test_settings() throws Exception {
        // given
        given(memberService.readAll()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/settings"))

                // then
                .andExpect(status().isOk())
                .andExpect(view().name("settings"));
    }

    @Test
    @DisplayName("/cart 로 GET 요청을 보낼 수 있다")
    void test_carts() throws Exception {
        // when
        mockMvc.perform(get("/cart"))

                // then
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }
}
