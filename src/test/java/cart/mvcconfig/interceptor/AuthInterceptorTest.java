package cart.mvcconfig.interceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import cart.controller.CartController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CartController.class)
class AuthInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/carts/**에 맵핑되는 uri는 모두 AuthInterceptor가 적용된다.")
    void preHandle() throws Exception {
        // when, then
        mockMvc.perform(get("/carts"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()));

        mockMvc.perform(post("/carts/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()));

        mockMvc.perform(delete("/carts/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()));
    }
}
