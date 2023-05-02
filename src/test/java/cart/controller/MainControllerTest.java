package cart.controller;

import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import cart.service.ProductService;
import cart.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("root directory 로 요청을 보내면 홈 html 화면을 보내준다")
    void rootPage() throws Exception {
        // given
        List<ProductEntity> allProducts = new ArrayList<>();
        given(productService.findAll())
                .willReturn(allProducts);

        // expect
        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", allProducts))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("'/admin' directory 로 요청을 보내면 admin html 화면을 보내준다")
    void admin() throws Exception {
        // given
        List<ProductEntity> allProducts = new ArrayList<>();
        given(productService.findAll())
                .willReturn(allProducts);

        // expect
        mockMvc.perform(get("/admin"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", allProducts))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("'/settings' directory 로 요청을 보내면 settings html 화면을 보내준다")
    void settings() throws Exception {
        // given
        List<UserEntity> allUsers = new ArrayList<>();
        given(userService.findAll())
                .willReturn(allUsers);

        // expect
        mockMvc.perform(get("/settings"))
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attribute("members", allUsers))
                .andExpect(status().isOk());
    }
}
