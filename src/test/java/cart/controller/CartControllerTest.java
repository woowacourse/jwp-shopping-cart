package cart.controller;

import cart.dto.cart.CartItemDto;
import cart.dto.cart.UserDto;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import cart.service.AuthService;
import cart.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    AuthService authService;
    @MockBean
    CartService cartService;

    @Test
    @DisplayName("사용자의 장바구니 상품 목록을 가져온다.")
    void findItemsTest() throws Exception {
        long cartId = 1L;
        CartItemDto expectDto1 = CartItemDto.fromCartIdAndProductEntity(
                cartId,
                new ProductEntity(5L, "item5", "url5.com", 5000));
        List<CartItemDto> expectDtos = List.of(expectDto1);

        when(authService.findMemberByEmail(anyString()))
                .thenReturn(UserDto.fromMemberEntity(new MemberEntity(1L, "a@a.com", "1234")));
        when(cartService.findAllUserItems(any(UserDto.class)))
                .thenReturn(expectDtos);

        mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic ZW1haWxAZW1haWwuY29tOjEyMzQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cartId").value(Math.toIntExact(cartId)))
                .andExpect(jsonPath("$..productId").value(Math.toIntExact(expectDto1.getProductDto().getId())))
                .andExpect(jsonPath("$..name").value(expectDto1.getProductDto().getName()))
                .andExpect(jsonPath("$..imgUrl").value(expectDto1.getProductDto().getImgUrl()))
                .andExpect(jsonPath("$..price").value(expectDto1.getProductDto().getPrice()));
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addItemTest() throws Exception {
        Long memberId = 1L;
        Long cartId = 1L;
        Long productId = 1L;
        UserDto expectUserDto = UserDto.fromMemberEntity(new MemberEntity(memberId, "a@a.com", "password1"));
        CartItemDto expectCartItemDto = CartItemDto.fromCartIdAndProductEntity(cartId, new ProductEntity(productId, "name1", "url1.com", 1000));

        when(authService.findMemberByEmail(anyString()))
                .thenReturn(expectUserDto);
        when(cartService.addItem(any(UserDto.class), anyLong()))
                .thenReturn(expectCartItemDto);

        mockMvc.perform(post("/cart/items/" + productId)
                        .header("Authorization", "Basic ZW1haWxAZW1haWwuY29tOjEyMzQ="))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartId").value(expectCartItemDto.getId()))
                .andExpect(jsonPath("$.productId").value(expectCartItemDto.getProductDto().getId()))
                .andExpect(jsonPath("$.name").value(expectCartItemDto.getProductDto().getName()))
                .andExpect(jsonPath("$.imgUrl").value(expectCartItemDto.getProductDto().getImgUrl()))
                .andExpect(jsonPath("$.price").value(expectCartItemDto.getProductDto().getPrice()));
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다.")
    void deleteItemTest() throws Exception {
        doNothing().when(cartService).deleteById(anyLong());
        mockMvc.perform(delete("/cart/items/" + anyLong())
                        .header("Authorization", "Basic ZW1haWxAZW1haWwuY29tOjEyMzQ="))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("인증 정보를 찾을 수 없으면 예외가 반환된다.")
    void authExceptionTest() throws Exception {
        mockMvc.perform(get("/cart/items")
                        .header(" ", " "))
                .andExpect(status().isBadRequest());
    }
}