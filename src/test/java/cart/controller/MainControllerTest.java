package cart.controller;

import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import cart.service.ProductService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
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
        // expect
        List<ProductEntity> allProducts = new ArrayList<>();
        given(productService.findAll())
                .willReturn(allProducts);

        // expect
        mockMvc.perform(get("/admin"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", allProducts))
                .andExpect(status().isOk());
    }

}
