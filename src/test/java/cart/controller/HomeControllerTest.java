package cart.controller;

import static org.junit.jupiter.api.Assertions.*;
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
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwpCartService jwpCartService;

    @Test
    @DisplayName("상품 목록 페이지를 조회한다.")
    void index() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

}
