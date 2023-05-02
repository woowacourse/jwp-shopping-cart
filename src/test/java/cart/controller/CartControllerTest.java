package cart.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.auth.service.AuthService;
import cart.domain.cart.service.CartService;
import cart.dto.AuthInfo;
import cart.dto.CartResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @MockBean
    private AuthService authService;
    @MockBean
    private CartService cartService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("장바구니 리스트 api")
    public void testGetCarts() throws Exception {
        //given
        final AuthInfo authInfo = new AuthInfo("test@test.com", "password");
        when(authService.checkAuthenticationHeader(anyString()))
            .thenReturn(authInfo);
        final List<CartResponse> cartResponses = List.of(
            new CartResponse(1L, "name1", "imageUrl1", 1000),
            new CartResponse(2L, "name2", "imageUrl2", 2000)
        );
        when(cartService.findByEmail(anyString()))
            .thenReturn(cartResponses);

        //when
        //then
        final MvcResult mvcResult = mockMvc.perform(get("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authentication", "Basic sdfksajdfklsdf"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    }
}
