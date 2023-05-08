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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        final List<CartItem> cartItemResponse = List.of(new CartItem(1L, "치킨", 1000, "www.abc.com"));
        given(userService.findByEmail(anyString())).willReturn(new User(1L, "test01@gmail.com", "12121212"));
        given(cartService.findByUserId(anyLong())).willReturn(cartItemResponse);

        mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic dGVzdDAxQGdtYWlsLmNvbToxMjEyMTIxMg=="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].productName", is("치킨")))
                .andExpect(jsonPath("$[0].productPrice", is(1000)))
                .andExpect(jsonPath("$[0].productImage", is("www.abc.com")));

        verify(cartService, times(1)).findByUserId(anyLong());
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
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/cart/items/1"));

        verify(cartService, times(1)).save(anyLong(), anyLong());
    }

    @Test
    @DisplayName("DELETE /cart/items/{id}")
    void deleteCartItem() throws Exception {
        doNothing().when(cartService).delete(anyLong());
        final int id = 1;

        mockMvc.perform(delete("/cart/items/" + id))
                .andExpect(status().isOk());

        verify(cartService, times(1)).delete(anyLong());
    }
}
