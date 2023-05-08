package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cart.dao.CartDao;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartDao cartDao;

    @Test
    @DisplayName("이미 존재하는 상품을 추가하면 IllegalStateException 예외가 발생한다")
    public void add_fail() {
        given(cartDao.findCartIdByMemberIdAndProductId(any(), any()))
            .willReturn(Optional.of(1L));

        assertThatThrownBy(() -> cartService.add(1L, 1L))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("장바구니에 이미 상품이 존재합니다.");
    }

    @Test
    @DisplayName("존재하지 않는 값을 삭제하려고 하면  NoSuchElementException 예외가 발생한다")
    public void delete_fail() {
        given(cartDao.deleteByMemberIdAndProductId(any(), any()))
            .willReturn(0);

        assertThatThrownBy(() -> cartService.delete(1L, 1L))
            .isInstanceOf(NoSuchElementException.class);
    }
}