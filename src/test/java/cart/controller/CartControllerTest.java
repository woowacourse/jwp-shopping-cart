package cart.controller;

import cart.controller.auth.dto.LoginUser;
import cart.controller.auth.dto.LoginUser;
import cart.controller.dto.CartResponse;
import cart.domain.*;
import cart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(
        controllers = CartController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = HandlerInterceptor.class)
        })
public class CartControllerTest {

    @MockBean
    CartService cartService;
    @MockBean
    CartController cartController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Mock
    CartService mockCartService;
    @InjectMocks
    CartController mockCartController;

    @DisplayName("POST /carts/{itemId} 요청 시 createCart 메서드 호출")
    @Test
    void createCartMappingURL() throws Exception {
        //given
        String email = "email@email.com";
        String password = "12345678";
        Mockito.doNothing().when(cartService).saveCart(email, 1L);
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/carts/1"));
        //given
        Mockito.verify(cartController, Mockito.times(1)).createCart(Mockito.any(), Mockito.anyLong());
    }

    @DisplayName("GET /carts 요청 시 readCart 메서드 호출")
    @Test
    void readCartMappingURL() throws Exception {
        //given
        String email = "email@email.com";
        String password = "12345678";
        User user = new User(new Email(email), new Password(password));
        Item item1 = new Item.Builder().id(1L).name(new Name("위키드")).imageUrl(new ImageUrl("https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg")).price(new Price(150000)).build();
        Item item2 = new Item.Builder().id(2L).name(new Name("마틸다")).imageUrl(new ImageUrl("https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif")).price(new Price(100000)).build();
        Item item3 = new Item.Builder().id(3L).name(new Name("빌리 엘리어트")).imageUrl(new ImageUrl("https://t1.daumcdn.net/cfile/226F4D4C544F42CF34")).price(new Price(200000)).build();
        CartResponse cartResponse1 = CartResponse.from(new Cart(1L, user, item1));
        CartResponse cartResponse2 = CartResponse.from(new Cart(2L, user, item2));
        CartResponse cartResponse3 = CartResponse.from(new Cart(3L, user, item3));
        Mockito.when(cartService.loadItemInsideCart(email)).thenReturn(List.of(cartResponse1, cartResponse2, cartResponse3));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/carts"));
        //given
        Mockito.verify(cartController, Mockito.times(1)).readCart(Mockito.any());
    }

    @DisplayName("DELETE /carts/{cartId} deleteCart 메서드 호출")
    @Test
    void deleteCartMappingURL() throws Exception {
        //given
        Mockito.doNothing().when(cartService).deleteCart("email@email.com", 1L);
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/1"));
        //given
        Mockito.verify(cartController, Mockito.times(1)).deleteCart(Mockito.any(), Mockito.anyLong());
    }

    @DisplayName("장바구니 추가 시, ResponseEntity<Void>를 응답한다.")
    @Test
    void createCart() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        LoginUser loginUser = new LoginUser(email, password);
        Long itemId = 1L;
        Mockito.doNothing().when(mockCartService).saveCart(email, itemId);
        //when
        ResponseEntity<Void> responseEntity = mockCartController.createCart(loginUser, itemId);
        //then
        assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(responseEntity.getHeaders().getLocation()).isEqualTo(URI.create("/"))
        );
    }

    @DisplayName("장바구니 조회 시,  ResponseEntity<List<CartResponse>>를 응답한다")
    @Test
    void loadAllItem() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        User user = new User(new Email(email), new Password(password));
        LoginUser loginUser = new LoginUser(email, password);
        Item item1 = new Item.Builder().id(1L).name(new Name("위키드")).imageUrl(new ImageUrl("https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg")).price(new Price(150000)).build();
        Item item2 = new Item.Builder().id(2L).name(new Name("마틸다")).imageUrl(new ImageUrl("https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif")).price(new Price(100000)).build();
        Item item3 = new Item.Builder().id(3L).name(new Name("빌리 엘리어트")).imageUrl(new ImageUrl("https://t1.daumcdn.net/cfile/226F4D4C544F42CF34")).price(new Price(200000)).build();
        CartResponse cartResponse1 = CartResponse.from(new Cart(1L, user, item1));
        CartResponse cartResponse2 = CartResponse.from(new Cart(2L, user, item2));
        CartResponse cartResponse3 = CartResponse.from(new Cart(3L, user, item3));
        when(mockCartService.loadItemInsideCart(email)).thenReturn(List.of(cartResponse1, cartResponse2, cartResponse3));
        //when
        ResponseEntity<List<CartResponse>> responseEntity = mockCartController.readCart(loginUser);
        //then
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getBody()).contains(cartResponse1, cartResponse2, cartResponse3)
        );
    }

    @DisplayName("장바구니 삭제 시,  ResponseEntity를 응답한다")
    @Test
    void deleteItem() {
        //given
        String email = "email@email.com";
        String password = "12345678";
        LoginUser loginUser = new LoginUser(email, password);
        Long cartId = 1L;
        doNothing().when(mockCartService).deleteCart("email@email.com", cartId);
        //when
        ResponseEntity<Void> responseEntity = mockCartController.deleteCart(loginUser, cartId);
        //then
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getHeaders().getLocation()).isEqualTo(URI.create("/"))
        );
    }
}
