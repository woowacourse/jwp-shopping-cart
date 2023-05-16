package cart.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cart.service.JwpCartService;

@WebMvcTest
class PageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwpCartService jwpCartService;

    @Test
    @DisplayName("상품 목록 페이지를 조회한다.")
    void index() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("관리자 페이지를 조회한다.")
    void admin() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/admin")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("설정 페이지를 조회한다.")
    void settings() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/settings")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("장바구니 페이지를 조회한다.")
    void cart() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/cart")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}
