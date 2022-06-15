package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static woowacourse.shoppingcart.application.ProductFixture.바나나;
import static woowacourse.shoppingcart.application.ProductFixture.사과;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
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
        CartItem 장바구니_바나나 = new CartItem(1L, new Name("바나나"), new Price(1000), new ImageUrl("banana.com"));
        CartItem 장바구니_사과 = new CartItem(2L, new Name("사과"), new Price(1500), new ImageUrl("apple.com"));

        given(cartItemDao.findCartItemsByCustomerId(any(Long.class))).willReturn(List.of(장바구니_바나나, 장바구니_사과));

        //when

        CartResponse cartResponse = cartService.findCartsByCustomerId(1L);
        //then
        assertThat(cartResponse.getCart().size()).isEqualTo(2);
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
