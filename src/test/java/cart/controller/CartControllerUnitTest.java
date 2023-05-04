package cart.controller;

import cart.dto.CartDto;
import cart.dto.CartRequest;
import cart.dto.ProductDto;
import cart.auth.AuthService;
import cart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    AuthService authService;

    @MockBean
    private CartService cartService;

    @Test
    void cart_save_success() throws Exception {
        CartRequest cartRequest = new CartRequest(1L, List.of(1L, 2L));

        mockMvc.perform(post("/carts")
                        .header("Authorization", "Basic" + Base64.encodeBase64String("test:pass".getBytes()))
                        .content(objectMapper.writeValueAsString(cartRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void cart_find_products_success() throws Exception {
        //given
        CartDto cartDto = new CartDto(1L, List.of(new ProductDto(2L, "name", "image", 3000L)));
        BDDMockito.given(cartService.findById(anyLong())).willReturn(cartDto);

        //then
        mockMvc.perform(get("/carts/1")
                        .header("Authorization", "Basic" + Base64.encodeBase64String("test:pass".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(2L))
                .andExpect(content().string(containsString("name")));
    }
}
