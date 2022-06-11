package woowacourse.shoppingcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.request.UpdateCartItemCountItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponseDto;
import woowacourse.shoppingcart.service.CartItemService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static woowacourse.fixture.Fixture.*;

class CartItemControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartItemService cartItemService;
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

        //given
        final CartItemResponseDto cartItemResponseDto1 = new CartItemResponseDto(1L, THUMBNAIL_URL, PRODUCT_NAME, PRICE, 10, 1);
        final CartItemResponseDto cartItemResponseDto2 = new CartItemResponseDto(2L, THUMBNAIL_URL, PRODUCT_NAME, PRICE, 10, 1);
        final List<CartItemResponseDto> cartItems = List.of(
                cartItemResponseDto1,
                cartItemResponseDto2
        );
        when(cartItemService.findCartItemsByCustomerId(any())).thenReturn(cartItems);

        //when
        final MockHttpServletResponse response = mockMvc.perform(get("/api/customers/1/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken))
                .andDo(print())
                .andReturn()
                .getResponse();

        //then
        final List<CartItemResponseDto> cartItemResponseDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(), CartItemResponseDto[].class));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(cartItemResponseDtos.size()).isEqualTo(2);
        assertThat(cartItemResponseDtos).extracting("productId", "name")
                .contains(tuple(1L, PRODUCT_NAME),
                        tuple(2L, PRODUCT_NAME));
    }

    @Test
    @DisplayName("장바구니에 물건을 담는다.")
    void addCartItem() throws Exception {

        //given
        doNothing().when(authService).checkAuthorization(any(), any());
        when(cartItemService.addCartItem(any(), any())).thenReturn(1L);
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(1L, 1);

        //when
        final MockHttpServletResponse response = mockMvc.perform(post("/api/customers/1/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .content(objectMapper.writeValueAsString(addCartItemRequestDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("장바구니에 담긴 물건을 삭제한다.")
    void deleteCartItem() throws Exception {

        //given
        doNothing().when(authService).checkAuthorization(any(), any());
        doNothing().when(cartItemService).deleteCartItem(any(), any());
        when(cartItemService.addCartItem(any(), any())).thenReturn(1L);

        //when
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(1L, 1);
        mockMvc.perform(post("/api/customers/1/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .content(objectMapper.writeValueAsString(addCartItemRequestDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        //then
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
    void updateCartItem() throws Exception {
        //given
        doNothing().when(authService).checkAuthorization(any(), any());
        when(cartItemService.addCartItem(any(), any())).thenReturn(1L);

        //when
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(1L, 1);
        mockMvc.perform(post("/api/customers/1/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .content(objectMapper.writeValueAsString(addCartItemRequestDto)))
                .andDo(print())
                .andReturn()
                .getResponse();

        //then
        final UpdateCartItemCountItemRequest updateCartItemCountItemRequest = new UpdateCartItemCountItemRequest(2);
        final MockHttpServletResponse response = mockMvc.perform(patch("/api/customers/1/carts?productId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .content(objectMapper.writeValueAsString(updateCartItemCountItemRequest)))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
