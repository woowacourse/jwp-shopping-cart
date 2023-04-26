package cart.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ViewController.class)
class ViewControllerTest {

    @MockBean
    ItemService itemService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("/을 요청하면 메인 페이지 이름을 반환한다.")
    void redirectMainPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", is("text/html;charset=UTF-8")))
                .andExpect(model().attribute("products", notNullValue()))
                .andExpect(view().name("index"))
                .andDo(print());
    }

    @Test
    @DisplayName("/admin을 요청하면 어드민 페이지 이름을 반환한다.")
    void redirectAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", is("text/html;charset=UTF-8")))
                .andExpect(model().attribute("products", notNullValue()))
                .andExpect(view().name("admin"))
                .andDo(print());
    }
}
