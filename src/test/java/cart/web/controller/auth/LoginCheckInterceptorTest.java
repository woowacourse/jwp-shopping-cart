package cart.web.controller.auth;

import cart.web.controller.cart.CartRestController;
import cart.web.service.CartService;
import cart.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CartRestController.class)
class LoginCheckInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    private static final String COOKIE_VALUE = "Basic YUBhLmNvbTpwYXNzd29yZDE=";


    @Test
    void name() throws Exception {
        // given, when
        Mockito.when(userService.isExistUser(Mockito.any())).thenReturn(false);
        Mockito.when(cartService.add(Mockito.any(), Mockito.any())).thenReturn(1L);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/1").header("Authorization", COOKIE_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}