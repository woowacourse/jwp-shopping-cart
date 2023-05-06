package cart.controller.domain;

import cart.auth.AuthenticationPrincipalArgumentResolver;
import cart.auth.BasicAuthInterceptor;
import cart.controller.domain.cart.CartItemsController;
import cart.dto.LoginDto;
import cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartItemsController.class)
class CartItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartService cartService;
    @MockBean
    AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    @MockBean
    BasicAuthInterceptor basicAuthInterceptor;

    private LoginDto loginDto;


    @BeforeEach
    void setUp() throws Exception {
        loginDto = new LoginDto(1L, "1", "2");

        when(basicAuthInterceptor.preHandle(any(), any(), any()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.supportsParameter(any()))
                .thenReturn(true);
        when(authenticationPrincipalArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn(loginDto);
    }

    @Test
    @DisplayName("get 요청시 ok 상태 코드를 반환하고 id에 해당하는 cart-items를 조회한다")
    void getCartItemsTest() throws Exception {
        mockMvc.perform(get("/cart-items")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).findAllCartItems(loginDto.getMemberId());
    }

    @Test
    @DisplayName("delete 요청시 no-content 상태 코드를 반환한다")
    void deleteCartItem() throws Exception {
        mockMvc.perform(delete("/cart-items/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(cartService, times(1)).deleteCartItem(1, loginDto.getMemberId());
    }
}
