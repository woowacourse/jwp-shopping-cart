package cart.controller;

import cart.dto.ProductResponseDto;
import cart.dto.UserResponseDto;
import cart.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @DisplayName("index View 테스트")
    @Test
    void indexTest() throws Exception {
        when(cartService.getProducts()).thenReturn(List.of(
                new ProductResponseDto(1, "image1", "name1", 1000),
                new ProductResponseDto(2, "image2", "name2", 2000)
        ));

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("index"));
    }

    @DisplayName("admin View 테스트")
    @Test
    void adminTest() throws Exception {
        when(cartService.getProducts()).thenReturn(List.of(
                new ProductResponseDto(1, "image1", "name1", 1000),
                new ProductResponseDto(2, "image2", "name2", 2000)
        ));

        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("admin"));
    }

    @DisplayName("settings View 테스트")
    @Test
    void settingsTest() throws Exception {
        when(cartService.getUsers()).thenReturn(List.of(
                new UserResponseDto("a@a.com", "password1"),
                new UserResponseDto("b@b.com", "password2")
        ));

        this.mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("members"))
                .andExpect(view().name("settings"));
    }
}
