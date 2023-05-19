package cart.controller;

import cart.auth.BasicAuthorizationExtractor;
import cart.auth.interceptor.LoginInterceptor;
import cart.auth.resolver.BasicAuthenticationPrincipalArgumentResolver;
import cart.controller.dto.auth.AuthInfoDto;
import cart.controller.dto.response.CartResponse;
import cart.domain.CartData;
import cart.domain.ImageUrl;
import cart.domain.Name;
import cart.domain.Price;
import cart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockBean
    CartService mockCartService;

    @MockBean
    CartController mockCartController;

    @MockBean
    LoginInterceptor loginInterceptor;

    @MockBean
    BasicAuthorizationExtractor extractor;

    @MockBean
    BasicAuthenticationPrincipalArgumentResolver basicAuthenticationPrincipalArgumentResolver;

    @Mock
    CartService cartService;

    @InjectMocks
    CartController cartController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("POST /carts/{itemId} 요청 시 addCart 메서드가 호출된다")
    @Test
    void addCartMappingURL() throws Exception {
        //given
        String value = objectMapper.writeValueAsString(new AuthInfoDto("test@email.com", "testPW"));
        when(mockCartService.saveCart(any(), anyLong())).thenReturn(1L);
        when(loginInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/carts/1")
                                              .header("Authorization", value));
        //then
        verify(mockCartController, times(1)).addCart(any(), anyLong());
    }

    @DisplayName("GET /carts 요청 시 loadAllCart 메서드가 호출된")
    @Test
    void loadAllCartMappingURL() throws Exception {
        //given
        String value = objectMapper.writeValueAsString(new AuthInfoDto("test@email.com", "testPW"));
        when(mockCartService.loadAllCart(any())).thenReturn(Collections.emptyList());
        when(loginInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/carts")
                                              .header("Authorization", value));
        //then
        verify(mockCartController, times(1)).loadAllCart(any());
    }

    @DisplayName("DELETE /carts/{cartId} 요청 시 deleteCart 메서드가 호출된")
    @Test
    void deleteCartMappingURL() throws Exception {
        //given
        String value = objectMapper.writeValueAsString(new AuthInfoDto("test@email.com", "testPW"));
        doNothing().when(mockCartService)
                   .deleteItem(anyLong());
        when(loginInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/1")
                                              .header("Authorization", value));
        //then
        verify(mockCartController, times(1)).deleteCart(anyLong());
    }

    @DisplayName("itemId를 입력받아 장바구니 추가 시 ResponseEntity를 응답한다")
    @Test
    void addCart() {
        //given
        AuthInfoDto value = new AuthInfoDto("test@email.com", "testPW");
        when(cartService.saveCart(any(), anyLong())).thenReturn(1L);
        //then
        ResponseEntity<Void> responseEntity = cartController.addCart(value, 1L);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(responseEntity.getHeaders()
                                               .getLocation()).isEqualTo(URI.create("/"))
        );
    }

    @DisplayName("장바구니 조회 시 ResponseEntity<List<CartResponse>>를 응답한다")
    @Test
    void loadAllCart() {
        //given
        List<CartResponse> carts = List.of(
                CartResponse.from(
                        new CartData(
                                1L,
                                new Name("1번"),
                                new ImageUrl("1번URL"),
                                new Price(150000)
                        )
                )
        );
        when(cartService.loadAllCart(any())).thenReturn(carts);
        //then
        ResponseEntity<List<CartResponse>> responseEntity = cartController.loadAllCart(new AuthInfoDto("test@email.com", "testPW"));
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getBody()).contains(carts.get(0))
        );
    }

    @DisplayName("cartID를 입력받아 장바구니 삭제 시 ResponseEntity를 응답한다")
    @Test
    void deleteCart() {
        //given
        Long cartId = 1L;
        doNothing().when(cartService)
                   .deleteItem(anyLong());
        //then
        ResponseEntity<Void> responseEntity = cartController.deleteCart(cartId);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getHeaders()
                                               .getLocation()).isEqualTo(URI.create("/"))
        );
    }
}
