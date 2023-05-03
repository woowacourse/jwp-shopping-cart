package cart.service;

import cart.dao.UserDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

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
        final String email = "존재하지 않는 이메일";

        //when
        when(userDao.findByEmail(email)).thenReturn(Optional.empty());

        //then
        assertAll(
                () -> assertThatThrownBy(() -> userService.addProductToCart(email, 1L))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("존재하지 않는 유저입니다."),
                () -> assertThatThrownBy(() -> userService.removeProductInCart(email, 1L))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("존재하지 않는 유저입니다."),
                () -> assertThatThrownBy(() -> userService.getAllProductsInCart(email))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("존재하지 않는 유저입니다."));
    }
}
