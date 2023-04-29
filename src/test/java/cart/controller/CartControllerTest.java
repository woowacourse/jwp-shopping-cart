package cart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cart.dto.ProductResponseDto;
import cart.service.CartService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

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
}
