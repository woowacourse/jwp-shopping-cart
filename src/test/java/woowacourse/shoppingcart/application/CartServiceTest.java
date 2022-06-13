package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.ImageUrl;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ProductName;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class CartServiceTest {

    private final Product 상품1 = new Product(1L, new ProductName("상품명1"), 1000, new ImageUrl("imageUrl1"));
    private final Product 상품2 = new Product(2L, new ProductName("상품명2"), 1000, new ImageUrl("imageUrl2"));

    private final CartService cartService;

    @Mock
    private CartItemDao cartItemDao;
    @Mock
    private ProductDao productDao;

    CartServiceTest() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(cartItemDao, productDao);
    }

    @DisplayName("특정 회원의 카트 목록을 불러온다.")
    @Test
    void findCartByCustomerId() {

        // given
        given(cartItemDao.findByCustomerId(1L))
                .willReturn(new Cart(List.of(상품1, 상품2)));

        // when
        final CartResponse cartResponse = cartService.findCartByCustomerId(1L);
        final List<ProductResponse> products = List.of(
                new ProductResponse(1L, "상품명1", 1000, "imageUrl1"),
                new ProductResponse(2L, "상품명2", 1000, "imageUrl2")
        );
        // then
        assertAll(
                () -> assertThat(cartResponse.getCart()).hasSize(2),
                () -> assertThat(cartResponse.getCart()).usingRecursiveComparison()
                        .isEqualTo(products)
        );
    }

    @DisplayName("특정 회원이 상품을 카트에 등록한다.")
    @Test
    void addCart() {
        // given
        given(productDao.findById(1L))
                .willReturn(Optional.of(상품1));
        given(cartItemDao.save(any(), any()))
                .willReturn(1L);

        // when
        final Long cartItemId = cartService.addCart(1L, 1L);

        // then
        assertThat(cartItemId).isEqualTo(1L);
    }

    @DisplayName("카트에 등록하려는 상품이 존재하지 않으면 예외를 발생한다.")
    @Test
    void throwWhenInvalidProduct() {
        // given
        given(productDao.findById(1L))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> cartService.addCart(1L, 1L))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    @DisplayName("특정 회원이 카트에 등록된 상품을 제거한다.")
    @Test
    void deleteCart() {
        // given
        given(cartItemDao.findProductIdsByCustomerId(1L))
                .willReturn(List.of(1L, 2L));
        given(cartItemDao.findIdByCustomerIdAndProductId(1L, 1L))
                .willReturn(1L);
        given(cartItemDao.deleteById(1L))
                .willReturn(1);

        // when
        final int affectedRows = cartService.deleteCart(1L, 1L);

        // then
        assertThat(affectedRows).isEqualTo(1);
    }

    @DisplayName("삭제하려는 상품이 카트에 포함되지 않으면 예외를 발생한다.")
    @Test
    void throwWhenProductNotContains() {
        // given
        given(cartItemDao.findIdsByCustomerId(1L))
                .willReturn(List.of(1L, 2L));

        // when
        assertThatThrownBy(() -> cartService.deleteCart(1L, 3L))
                .isInstanceOf(NotInCustomerCartItemException.class)
                .hasMessage("장바구니 아이템이 없습니다.");
    }

    @DisplayName("삭제된 상품의 개수가 1이 아니면 예외를 발생한다.")
    @Test
    void throwWhenIncorrectDeletedRows() {
        // given
        given(cartItemDao.findProductIdsByCustomerId(1L))
                .willReturn(List.of(1L, 2L));
        given(cartItemDao.findIdByCustomerIdAndProductId(1L, 1L))
                .willReturn(1L);
        given(cartItemDao.deleteById(1L))
                .willReturn(0);
        // when
        assertThatThrownBy(() -> cartService.deleteCart(1L, 1L))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("유효하지 않은 장바구니입니다.");
    }
}
