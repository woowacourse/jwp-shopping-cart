package cart.service;

import cart.auth.UserInfo;
import cart.dao.UserDao;
import cart.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @DisplayName("존재하지 않는 유저 이메일로 장바구니 추가, 제거, 조회를 하면 예외가 발생한다")
    @Test
    void notExistEmail() {
        //given
        final UserInfo userInfo = new UserInfo("존재하지 않는 이메일", "password");

        //when
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());

        //then
        assertAll(
                () -> assertThatThrownBy(() -> userService.addProductToCart(userInfo, 1L))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("잘못된 유저 정보입니다."),
                () -> assertThatThrownBy(() -> userService.removeProductInCart(userInfo, 1L))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("잘못된 유저 정보입니다."),
                () -> assertThatThrownBy(() -> userService.getAllProductsInCart(userInfo))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("잘못된 유저 정보입니다."));
    }

    @DisplayName("존재하지 않는 상품을 장바구니에 추가하면 예외가 발생한다")
    @Test
    void notExistProduct() {
        //given
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(new User(1L, "email@email", "password")));
        when(userDao.addProductToCart(anyLong(), anyLong())).thenThrow(DataIntegrityViolationException.class);

        //then
        assertThatThrownBy(() -> userService.addProductToCart(new UserInfo("email@email", "password"), 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }
}
