package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static woowacourse.TestFixture.customer;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.dto.CartUpdationRequest;
import woowacourse.shoppingcart.exception.bodyexception.DuplicatedProductInCartException;
import woowacourse.shoppingcart.exception.bodyexception.NotExistProductInCartException;
import woowacourse.shoppingcart.exception.nobodyexception.NotFoundProductException;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    @DisplayName("사용자의 장바구니에 상품을 추가한다.")
    @Test
    void addCart_success_void() {
        // given
        Long productId = 1L;
        Product product = new Product(productId, "product1", 1000, "imageUrl1");

        given(cartItemDao.existProduct(customer.getId(), productId))
                .willReturn(false);
        given(productService.findProductById(productId))
                .willReturn(product);

        // when, then
        assertThatCode(() -> cartService.addCart(product.getId(), customer))
                .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 물품을 장바구니에 추가하면 예외가 발생한다.")
    @Test
    void addCart_notExistProduct_exceptionThrown() {
        // given
        Long notExistProductId = 21L;

        given(productService.findProductById(notExistProductId))
                .willThrow(new NotFoundProductException());

        // when, then
        assertThatThrownBy(() -> cartService.addCart(notExistProductId, customer))
                .isInstanceOf(NotFoundProductException.class);
    }

    @DisplayName("장바구니에 등록된 물품을 중복 추가하려면 예외가 발생한다.")
    @Test
    void addCart_duplicatedProduct_exceptionThrown() {
        // given
        given(cartItemDao.existProduct(1L, 1L))
                .willThrow(new DuplicatedProductInCartException());

        // when, then
        assertThatThrownBy(() -> cartService.addCart(1L, customer))
                .isInstanceOf(DuplicatedProductInCartException.class)
                .hasMessage("중복된 물품입니다.");
    }

    @DisplayName("장바구니에 상품 목록을 조회한다.")
    @Test
    void getCarts_existProducts_return() {
        // given
        List<Long> productIds = List.of(1L, 2L, 3L);

        Product product1 = new Product(1L, "product1", 1000, "url1");
        Product product2 = new Product(2L, "product2", 2000, "url2");
        Product product3 = new Product(3L, "product3", 3000, "url3");

        Cart cart1 = new Cart(1L, product1, 5);
        Cart cart2 = new Cart(2L, product2, 2);
        Cart cart3 = new Cart(3L, product3, 7);

        given(cartItemDao.getCartsByCustomerId(1L))
                .willReturn(List.of(cart1, cart2, cart3));

        // when
        List<Cart> carts = cartService.getCarts(customer);

        // then
        assertThat(carts).containsExactly(cart1, cart2, cart3);
    }

    @DisplayName("장바구니에서 상품을 수정한다.")
    @Test
    void updateProductInCart_success_cartReturned() {
        // given
        Long productId = 1L;
        Product product1 = new Product(productId, "product1", 1000, "url1");

        Long cartId = 1L;
        CartUpdationRequest request = new CartUpdationRequest(5);
        Cart updatedCart = new Cart(cartId, product1, request.getQuantity());

        given(cartItemDao.existProduct(customer.getId(), productId))
                .willReturn(true);

        given(productService.findProductById(productId))
                .willReturn(product1);

        given(cartItemDao.findCartByProductCustomer(customer.getId(), productId))
                .willReturn(updatedCart);

        // when
        Cart cart = cartService.updateProductInCart(customer, request, productId);

        // then
        assertThat(cart.getQuantity()).isEqualTo(5);
    }

    @DisplayName("장바구니에 존재하지 않는 상품을 수정하려고 하면 예외가 발생한다.")
    @Test
    void updateProductInCart_notExist_exceptionThrown() {
        //given
        CartUpdationRequest request = new CartUpdationRequest(4);
        Product product = new Product(1L, "product9", 9000, "url9");

        given(productService.findProductById(1L))
                .willReturn(product);
        given(cartItemDao.existProduct(1L, 1L))
                .willReturn(false);

        // when, then
        assertThatThrownBy(() -> cartService.updateProductInCart(customer, request, 1L))
                .isInstanceOf(NotExistProductInCartException.class);
    }

    @DisplayName("장바구니에서 상품을 삭제한다.")
    @Test
    void deleteProductInCart_success_void() {
        // given
        Product product1 = new Product(1L, "product1", 1000, "url1");

        given(productService.findProductById(1L))
                .willReturn(product1);
        given(cartItemDao.existProduct(1L, 1L))
                .willReturn(true);

        // when, then
        assertThatCode(() -> cartService.deleteProductInCart(customer, product1.getId()))
                .doesNotThrowAnyException();
    }

    @DisplayName("장바구니에 존재하지 않는 상품을 삭제하려고 하면 예외가 발생한다.")
    @Test
    void deleteProductInCart_notExist_exceptionThrown() {
        //given
        Product product = new Product(9L, "product9", 9000, "url9");

        given(productService.findProductById(9L))
                .willReturn(product);
        given(cartItemDao.existProduct(1L, 9L))
                .willThrow(new NotExistProductInCartException());

        // when, then
        assertThatThrownBy(() -> cartService.deleteProductInCart(customer, 9L))
                .isInstanceOf(NotExistProductInCartException.class);
    }
}
