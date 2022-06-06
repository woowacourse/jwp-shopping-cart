package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static woowacourse.shoppingcart.application.ProductFixture.바나나;
import static woowacourse.shoppingcart.application.ProductFixture.사과;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;

class CartServiceTest {

    private final CartService cartService;
    @Mock
    private CartItemDao cartItemDao;
    @Mock
    private ProductDao productDao;

    public CartServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.cartService = new CartService(cartItemDao, productDao);
    }
    
    @Test
    @DisplayName("회원 아이디에 따라 가진 카트를 반환한다.")
    void findCartsByCustomerId() {
        //given
        given(cartItemDao.findIdsByCustomerId(any(Long.class))).willReturn(List.of(1L, 2L));
        given(cartItemDao.findProductIdById(1L)).willReturn(Optional.of(1L));
        given(cartItemDao.findProductIdById(2L)).willReturn(Optional.of(2L));
        given(productDao.findProductById(1L)).willReturn(Optional.of(바나나));
        given(productDao.findProductById(2L)).willReturn(Optional.of(사과));
        //when

        CartResponse cartResponse = cartService.findCartsByCustomerId(1L);
        //then
        assertThat(cartResponse.getCart().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 아이디가 존재하지 않으면 예외를 반환한다.")
    void notExistCustomerId() {
        //given
        given(cartItemDao.findIdsByCustomerId(1L)).willReturn(new ArrayList<>());
        //when

        //then
        assertThatThrownBy(() -> cartService.findCartsByCustomerId(1L))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("cart id 가 존재하지 않으면 예외를 반환한다.")
    void notExistCartId() {
        //given
        given(cartItemDao.findIdsByCustomerId(1L)).willReturn(List.of(1L, 2L));
        given(cartItemDao.findProductIdById(1L)).willReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(() -> cartService.findCartsByCustomerId(1L))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("유효하지 않은 장바구니입니다.");
    }

    @Test
    @DisplayName("cart id 에 따른 product id 가 존재하지 않으면 예외를 반환한다.")
    void notExistProductId() {
        //given
        given(cartItemDao.findIdsByCustomerId(1L)).willReturn(List.of(1L, 2L));
        given(cartItemDao.findProductIdById(1L)).willReturn(Optional.of(1L));
        given(cartItemDao.findProductIdById(2L)).willReturn(Optional.of(2L));
        given(productDao.findProductById(1L)).willReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(() -> cartService.findCartsByCustomerId(1L))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("올바르지 않은 상품 아이디 입니다.");
    }

    @Test
    @DisplayName("카트에 상품을 추가한다.")
    void addCart() {
        //given
        given(cartItemDao.addCartItem(1L, 1L)).willReturn(1L);
        //when
        Long cartId = cartService.addCart(1L, 1L);
        //then
        assertThat(cartId).isEqualTo(1L);
    }

    @Test
    @DisplayName("카트를 삭제한다.")
    void deleteCart() {
        //given
        given(cartItemDao.deleteCartItem(1L, 1L)).willReturn(1);
        //when
        int affectedQuery = cartService.deleteCart(1L, 1L);
        //then
        assertThat(affectedQuery).isEqualTo(1);
    }
}
