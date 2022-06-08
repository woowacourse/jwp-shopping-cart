package woowacourse.shoppingcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.request.UpdateCustomerDto;
import woowacourse.shoppingcart.dto.response.CartItemResponseDto;
import woowacourse.shoppingcart.service.CartService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static woowacourse.fixture.Fixture.*;

class CartItemControllerTest extends ControllerTest{

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CartItemController cartItemController;
    @MockBean
    private CartService cartService;
    @MockBean
    private AuthService authService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = jwtTokenProvider.createToken(TEST_EMAIL);
    }

    @Test
    @DisplayName("장바구니에 담긴 물건들을 가져온다.")
    void getCartItems() throws Exception {
        final List<CartItemResponseDto> cartItems = List.of(
                new CartItemResponseDto(1L, THUMBNAIL_URL, PRODUCT_NAME, PRICE, 10, 1),
                new CartItemResponseDto(2L, THUMBNAIL_URL, PRODUCT_NAME, PRICE, 10, 1)
        );
        when(cartService.findCartsByCustomerId(any())).thenReturn(cartItems);

        final MockHttpServletResponse response = mockMvc.perform(get("/api/customers/1/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION,BEARER + accessToken))
                .andDo(print())
                .andReturn()
                .getResponse();

        final List<CartItemResponseDto> cartItemResponseDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(), CartItemResponseDto[].class));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(cartItemResponseDtos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니에 물건을 담는다.")
    void addCartItem() throws Exception {
        doNothing().when(authService).checkAuthorization(any(), any());
        when(cartService.addCart(any(), any())).thenReturn(1L);

        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(1L, 1);

        final MockHttpServletResponse response = mockMvc.perform(post("/api/customers/1/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .content(objectMapper.writeValueAsString(addCartItemRequestDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("장바구니에 담긴 물건을 삭제한다.")
    void deleteCartItem() throws Exception{
        doNothing().when(authService).checkAuthorization(any(), any());
        doNothing().when(cartService).deleteCart(any(), any());
        when(cartService.addCart(any(), any())).thenReturn(1L);

        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(1L, 1);
        mockMvc.perform(post("/api/customers/1/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .content(objectMapper.writeValueAsString(addCartItemRequestDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        final MockHttpServletResponse response = mockMvc.perform(delete("/api/customers/1/carts?productId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("장바구니에 담긴 물건의 수량을 수정한다.")
    void updateCartItem() {

    }
}
