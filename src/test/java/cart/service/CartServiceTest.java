package cart.service;

import cart.controller.dto.auth.AuthInfo;
import cart.controller.dto.response.CartResponse;
import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.UserDao;
import cart.domain.*;
import cart.exception.NotFoundResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartDao cartDao;
    @Mock
    private UserDao userDao;
    @Mock
    private ItemDao itemDao;

    @InjectMocks
    private CartService cartService;

    @DisplayName("장바구니를 저장한다")
    @Test
    void saveCart() {
        //given
        Cart cart = new Cart.Builder().userId(1L)
                                      .itemId(1L)
                                      .build();
        when(cartDao.save(cart)).thenReturn(1L);
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(
                new User.Builder()
                        .id(1L)
                        .email(new Email("test@gmail.com"))
                        .password(new Password("testPW"))
                        .build()
        ));
        when(itemDao.findBy(anyLong())).thenReturn(Optional.of(
                new Item.Builder()
                        .id(1L)
                        .name(new Name("test"))
                        .imageUrl(new ImageUrl("testUrl"))
                        .price(new Price(150000))
                        .build()
        ));
        //when
        Long id = cartService.saveCart(new AuthInfo("test@email.com", "testPW"), 1L);
        //then
        assertThat(id).isEqualTo(1L);
    }

    @DisplayName("특정 사용자의 모든 장바구니를 불러온다")
    @Test
    void loadAllCart() {
        //given
        CartData cartData = new CartData(1L, new Name("1번"), new ImageUrl("1번URL"), new Price(100000));
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(
                new User.Builder()
                        .id(1L)
                        .email(new Email("test@gmail.com"))
                        .password(new Password("testPW"))
                        .build()
        ));
        when(cartDao.findAll(1L)).thenReturn(List.of(cartData));
        //when
        List<CartResponse> carts = cartService.loadAllCart(new AuthInfo("test@email.com", "testPW"));
        //then
        assertThat(carts).contains(CartResponse.from(cartData));
    }

    @DisplayName("장바구니를 삭제한다")
    @Test
    void deleteItem() {
        //given
        Cart cart = new Cart.Builder().userId(1L)
                                      .itemId(1L)
                                      .build();
        when(cartDao.findBy(1L)).thenReturn(Optional.of(cart));
        doNothing().when(cartDao)
                   .deleteBy(1L);
        //when
        cartService.deleteItem(1L);
        //then
        verify(cartDao, times(1)).deleteBy(1L);
    }

    @DisplayName("없는 장바구니를 삭제하면 에외가 발생한다")
    @Test
    void deleteItemExceptionWithNotExist() {
        //given
        when(cartDao.findBy(100L)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() ->
                cartService.deleteItem(100L)
        ).isInstanceOf(NotFoundResultException.class);
    }
}
