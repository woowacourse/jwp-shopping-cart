package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.exception.notfound.CartProductNotFoundException;
import cart.exception.notfound.ProductNotFoundException;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// TODO: 픽스쳐 적용
class CartServiceTest {

    private CartDao cartDao;
    private ProductDao productDao;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartDao = Mockito.mock(CartDao.class);
        productDao = Mockito.mock(ProductDao.class);
        cartService = new CartService(cartDao, productDao);
    }

    @Test
    @DisplayName("존재하지 않는 상품을 담을시 예외가 발생한다")
    void addNotExistProduct_throws() {
        // given
        long memberId = 1;
        long productId = 2;
        Member member = new Member(memberId, "a@a.com", "password1", "애쉬");
        given(productDao.findById(productId))
                .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> cartService.addProduct(member, productId))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("상품을 중복으로 담을시 예외가 발생한다")
    void addDuplicatedProduct_throws() {
        // given
        long memberId = 1;
        long productId = 2;
        Member member = new Member(memberId, "a@a.com", "password1", "애쉬");
        given(productDao.findById(productId))
                .willReturn(Optional.of(new Product(productId, "피자", 1000, null)));
        given(cartDao.findByMemberIdAndProductId(memberId, productId))
                .willReturn(Optional.of(new Cart((long) 1, memberId, productId)));

        // when then
        assertThatThrownBy(() -> cartService.addProduct(member, productId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 장바구니에 담은 상품입니다.");
    }

    @Test
    @DisplayName("장바구니에 상품을 담는다")
    void addProduct() {
        // given
        long memberId = 1;
        long productId = 2;
        Member member = new Member(memberId, "a@a.com", "password1", "애쉬");
        given(productDao.findById(productId))
                .willReturn(Optional.of(new Product(productId, "피자", 1000, null)));
        given(cartDao.findByMemberIdAndProductId(memberId, productId))
                .willReturn(Optional.empty());

        // when then
        assertThatNoException().isThrownBy(() -> cartService.addProduct(member, productId));
    }

    @Test
    @DisplayName("담은 상품이 없으면 빈 배열을 반환한다")
    void cartEmptyFind() {
        // given
        long memberId = 1;
        Member member = new Member(memberId, "a@a.com", "password1", "애쉬");
        given(cartDao.findAllByMemberId(memberId))
                .willReturn(Collections.emptyList());

        // when then
        assertThat(cartService.findCartProducts(member)).isEmpty();
    }

    @Test
    @DisplayName("장바구니 삭제시 담은 상품이 아니면 예외 발생")
    void deleteInvalidId() {
        // given
        long memberId = 1;
        long productId = 2;
        Member member = new Member(memberId, "a@a.com", "password1", "애쉬");
        given(cartDao.findByMemberIdAndProductId(memberId, productId))
                .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> cartService.delete(member, productId))
                .isInstanceOf(CartProductNotFoundException.class);
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다")
    void delete() {
        // given
        long memberId = 1;
        long productId = 2;
        Member member = new Member(memberId, "a@a.com", "password1", "애쉬");
        given(cartDao.findByMemberIdAndProductId(memberId, productId))
                .willReturn(Optional.of(new Cart((long) 1, memberId, productId)));

        // when then
        assertThatNoException().isThrownBy(() -> cartService.delete(member, productId));
    }
}
