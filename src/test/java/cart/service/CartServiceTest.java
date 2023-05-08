package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dao.UserDao;
import cart.dto.ProductResponseDto;
import cart.dto.UserResponseDto;
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;

    @DisplayName("모든 User를 가져온다.")
    @Test
    void getUsersTest() {
        when(userDao.selectAll())
                .thenReturn(List.of(new UserEntity(1, "email", "password"), new UserEntity(2, "email2", "password2")));

        List<UserResponseDto> users = cartService.getUsers();
        assertThat(users).hasSize(2);
    }

    @DisplayName("userId를 입력하여 해당 유저의 카트에 담긴 항목을 가져온다")
    @Test
    void getCartItemsByUserId() {
        when(cartDao.selectByUserId(1))
                .thenReturn(List.of(new CartEntity(1, 1), new CartEntity(1, 2)));

        when(productDao.selectById(1))
                .thenReturn(new ProductEntity(1, "name", 1000, "image"));

        when(productDao.selectById(2))
                .thenReturn(new ProductEntity(2, "name2", 2000, "image2"));

        List<ProductResponseDto> cartItems = cartService.getCartItems(1);

        assertAll(
                () -> assertThat(cartItems).hasSize(2),
                () -> assertThat(cartItems.get(0).getName()).isEqualTo("name"),
                () -> assertThat(cartItems.get(0).getPrice()).isEqualTo(1000),
                () -> assertThat(cartItems.get(0).getImage()).isEqualTo("image"),
                () -> assertThat(cartItems.get(1).getName()).isEqualTo("name2"),
                () -> assertThat(cartItems.get(1).getPrice()).isEqualTo(2000),
                () -> assertThat(cartItems.get(1).getImage()).isEqualTo("image2")
        );
    }

    @DisplayName("유저의 장바구니에 아이템을 추가한다.")
    @Test
    void addCartItemTest() {
        when(cartDao.insert(any()))
                .thenReturn(1);

        assertThat(cartService.addCartItem(1, 1)).isEqualTo(1);
    }

    @DisplayName("유저의 장바구니에서 아이템을 삭제하도록 요청했을 때 성공한다.")
    @Test
    void deleteCartItemSuccessTest() {
        when(cartDao.delete(1, 1))
                .thenReturn(1);

        assertThat(cartService.deleteCartItem(1, 1))
                .isEqualTo(1);
    }

    @DisplayName("유저의 장바구니에서 아이템을 삭제하도록 요청했을 때 삭제할 아이템이 존재하지 않는다.")
    @Test
    void deleteCartItemFailureTest() {
        when(cartDao.delete(1, 1))
                .thenReturn(0);

        assertThatThrownBy(() -> cartService.deleteCartItem(1, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
