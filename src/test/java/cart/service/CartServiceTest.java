package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CartServiceTest {

    @Mock
    ProductDao productDao;

    @Mock
    CartDao cartDao;

    @InjectMocks
    CartService cartService;

    @DisplayName("장바구니 조회")
    @Test
    void findAll() {
        int memberId = 1;

        when(cartDao.findCartByMemberId(memberId))
                .thenReturn(List.of());

        cartService.findAll(memberId);

        verify(cartDao, atLeastOnce()).findCartByMemberId(memberId);
    }

    @DisplayName("장바구니를 삭제할 수 있다")
    @Test
    void delete() {
        int cartId = 1;
        int memberId = 1;

        cartService.deleteCart(cartId, memberId);

        verify(cartDao, atLeast(1)).deleteById(anyInt(), anyInt());
    }

    @DisplayName("장바구니 추가 테스트")
    @Test
    void insert() {
        int memberId = 1;
        int existProductId = 99;

        ProductEntity productEntity = new ProductEntity(existProductId, "테스트", "테스트", 1000);

        when(productDao.findById(existProductId))
                .thenReturn(Optional.of(productEntity));

        cartService.insertCart(existProductId, memberId);

        verify(productDao, atLeastOnce()).findById(existProductId);
    }

    @DisplayName("존재하지 않은 상품을 장바구니에 추가하려고 할 경우 예외 테스트")
    @Test
    void insertFail() {
        int memberId = 1;
        int notExistProductId = 99;

        when(productDao.findById(notExistProductId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.insertCart(notExistProductId, memberId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 id를 확인해주세요.");
    }

}
