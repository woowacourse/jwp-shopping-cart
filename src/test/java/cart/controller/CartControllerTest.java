package cart.controller;

import cart.domain.cart.CartItem;
import cart.domain.user.User;
import cart.dto.CartItemRequest;
import cart.service.CartService;
import cart.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartService cartService;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("GET /cart/items")
    void showCartItemList() throws Exception {
        final List<CartItem> cartItemResponse = List.of(new CartItem(1L, "치킨", 1000, null));
        final String result = objectMapper.writeValueAsString(cartItemResponse);

        given(userService.findByEmail(anyString())).willReturn(new User(1L, "test01@gmail.com", "12121212"));
        given(cartService.findByUserId(anyLong())).willReturn(cartItemResponse);

        mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic dGVzdDAxQGdtYWlsLmNvbToxMjEyMTIxMg=="))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    @DisplayName("POST /cart/items")
    void createCartItem() throws Exception {
        final CartItemRequest cartItemRequest = new CartItemRequest(1L);
        final String request = objectMapper.writeValueAsString(cartItemRequest);
        given(userService.findByEmail(anyString())).willReturn(new User(1L, "test01@gmail.com", "12121212"));
        given(cartService.save(anyLong(), anyLong())).willReturn(1L);

        mockMvc.perform(post("/cart/items")
                        .header("Authorization", "Basic dGVzdDAxQGdtYWlsLmNvbToxMjEyMTIxMg==")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("DELETE /cart/items/{id}")
    void deleteCartItem() throws Exception {
        doNothing().when(cartService).delete(anyLong());
        final int id = 1;

        mockMvc.perform(delete("/cart/items/" + id))
                .andExpect(status().isOk());
    }
}
