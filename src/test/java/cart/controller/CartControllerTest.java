package cart.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.MemberInfo;
import cart.service.AuthService;
import cart.service.CartService;
import cart.service.MemberService;
import java.util.Base64;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private AuthService authService;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        given(cartService.addProduct(any(), any())).willReturn(1L);
        given(authService.extractMemberInfo(anyString())).willReturn(new MemberInfo("email@email", "password"));
        given(memberService.findIdByMemberInfo(any())).willReturn(1L);
    }

    @Test
    void addProduct() throws Exception {
        String authString = "email@email" + ":" + "password";
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        mockMvc.perform(post("/carts/" + 1)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuthString))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(status().isCreated());
    }
}
