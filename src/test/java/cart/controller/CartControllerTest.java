package cart.controller;

import cart.BasicAuthorizationEncoder;
import cart.auth.dto.AuthenticationDto;
import cart.auth.service.AuthService;
import cart.dto.request.CartRequestDto;
import cart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
    private AuthService authService;

    private String authenticationHeader;
    private int userId;

    @BeforeEach
    void setUp() {
        final AuthenticationDto authenticationDto = new AuthenticationDto("ditoo@wooteco.com", "ditoo1234");
        authenticationHeader = BasicAuthorizationEncoder.encode(
                authenticationDto.getEmail(), authenticationDto.getPassword());
        userId = 1;
    }

    @Test
    @DisplayName("장바구니에 추가 성공")
    void create_success() throws Exception {
        //given
        final CartRequestDto requestDto = new CartRequestDto(2);
        final String requestBody = objectMapper.writeValueAsString(requestDto);
        given(cartService.create(requestDto, userId))
                .willReturn(1);

        //expect
        mockMvc.perform(post("/cart")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", authenticationHeader))
                .andExpect(header().string("Location", "/"))
                .andExpect(status().isCreated());
    }
}