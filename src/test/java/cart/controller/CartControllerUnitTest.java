package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.auth.AuthInfo;
import cart.auth.AuthService;
import cart.service.CartService;
import cart.service.CustomerService;
import cart.service.dto.CartRequest;
import cart.service.dto.CartResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
class CartControllerUnitTest {

    @MockBean
    private CartService cartService;
    @MockBean
    private AuthService authService;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static String encodedString;

    static {
        String testValue = "email:password";
        byte[] encodedBytes = Base64.encodeBase64(testValue.getBytes());
        encodedString = new String(encodedBytes);
    }

    @BeforeEach
    void setUp() {
        given(customerService.findIdByEmail(anyString())).willReturn(1L);
        given(customerService.isAbleToLogin(anyString(), anyString())).willReturn(true);
        given(authService.resolveAuthInfo(anyString())).willReturn(new AuthInfo("email", "password"));
    }

    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    @Test
    void addProductToCart() throws Exception {
        // given
        given(cartService.save(any(CartRequest.class), anyLong())).willReturn(1L);
        String requestString = objectMapper.writeValueAsString(new CartRequest(1L));
        // when, then
        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestString)
                        .header("Authorization", "Basic " + encodedString))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/cart/1"));
    }

    @DisplayName("사용자의 장바구니를 조회할 수 있다.")
    @Test
    void viewAllProductsOfCustomer() throws Exception {
        // given
        List<CartResponse> carts = List.of(new CartResponse(1L, "baron", "tmpUrl", 2000));
        String responseString = objectMapper.writeValueAsString(carts);
        given(cartService.findAllByCustomerId(anyLong())).willReturn(carts);

        // when, then
        mockMvc.perform(get("/cart/products")
                        .header("Authorization", "Basic " + encodedString))
                .andExpect(status().isOk())
                .andExpect(content().string(responseString));
    }

    @DisplayName("장바구니 상품을 삭제할 수 있다.")
    @Test
    void deleteCustomerCartProduct() throws Exception {
        // when, then
        willDoNothing().given(cartService).deleteById(anyLong());
        mockMvc.perform(delete("/cart/1")
                        .header("Authorization", "Basic " + encodedString))
                .andExpect(status().isNoContent());
    }
}