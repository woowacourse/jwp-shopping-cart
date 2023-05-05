package cart.service;

import cart.controller.dto.CartResponse;
import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.UserDao;
import cart.domain.*;
import com.sun.source.tree.ModuleTree;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartDao cartDao;
    @Mock
    private UserDao userDao;
    @Mock
    private ItemDao itemDao;
    @InjectMocks
    private CartService cartService;

    @DisplayName("email과 itemId를 받아 Cart를 저장한다.")
    @Test
    void saveCart() {
        //given
        String email = "email1@email.com";
        String password = "12345678";
        User user = new User(1L, new Email(email), new Password(password));
        Mockito.when(userDao.findBy(email)).thenReturn(Optional.of(user));
        Long itemId = 1L;
        String name = "위키드";
        String imageUrl = "https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg";
        int price = 150000;
        Item item = new Item.Builder().id(itemId).name(new Name(name)).imageUrl(new ImageUrl(imageUrl)).price(new Price(price)).build();
        Mockito.when(itemDao.findBy(itemId)).thenReturn(Optional.of(item));
        //when
        cartService.saveCart(email, itemId);
        //then
        Mockito.verify(cartDao, Mockito.times(1)).save(Mockito.any());
    }

    @DisplayName("email로 특정 유저의 장바구니 정보를 가져온다.")
    @Test
    void loadItemInsideCart() {
        //given
        String email = "email1@email.com";
        String password = "12345678";
        User user = new User(1L, new Email(email), new Password(password));
        Mockito.when(userDao.findBy(email)).thenReturn(Optional.of(user));
        Item item1 = new Item.Builder().id(1L).name(new Name("위키드")).imageUrl(new ImageUrl("https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg")).price(new Price(150000)).build();
        Item item2 = new Item.Builder().id(2L).name(new Name("마틸다")).imageUrl(new ImageUrl("https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif")).price(new Price(100000)).build();
        Item item3 = new Item.Builder().id(3L).name(new Name("빌리 엘리어트")).imageUrl(new ImageUrl("https://t1.daumcdn.net/cfile/226F4D4C544F42CF34")).price(new Price(200000)).build();
        List<Cart> carts = List.of(
                new Cart(1L, user, item1),
                new Cart(2L, user, item2),
                new Cart(3L, user, item3)
        );
        Mockito.when(cartDao.findBy(user.getId())).thenReturn(carts);
        //when
        List<CartResponse> cartResponses = cartService.loadItemInsideCart(email);
        //given
        Assertions.assertThat(cartResponses).hasSize(3);
    }

    @DisplayName("cartId로 장바구니 정보를 삭제한다")
    @Test
    void deleteCart() {
        //given
        Long cartId = 1L;
        Mockito.doNothing().when(cartDao).delete(cartId);
        //when
        cartService.deleteCart(cartId);
        //then
        Mockito.verify(cartDao, Mockito.times(1)).delete(cartId);
    }
}
