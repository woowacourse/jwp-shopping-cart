package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.shoppingcart.application.dto.AddCartServiceRequest;
import woowacourse.shoppingcart.application.dto.UpdateQuantityServiceRequest;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidCartQuantityException;
import woowacourse.shoppingcart.exception.NotInMemberCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;
import woowacourse.shoppingcart.ui.dto.CartResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CartServiceTest {

    private final CartService cartService;
    private final CartDao cartDao;

    public CartServiceTest(CartService cartService, CartDao cartDao) {
        this.cartService = cartService;
        this.cartDao = cartDao;
    }

    @DisplayName("올바른 데이터로 장바구니를 동록하면 장바구니 등록 ID를 반환한다.")
    @Test
    void add() {
        AddCartServiceRequest request = new AddCartServiceRequest(3L, 1L);
        long cartId = cartService.add(request);
        assertThat(cartId).isEqualTo(6L);
    }

    @DisplayName("중복된 상품을 장바구니에 동록하면 해당 상품의 개수가 1개 증가한다.")
    @Test
    void addDuplicateProduct() {
        long memberId = 3L;
        long productId = 1L;
        AddCartServiceRequest request = new AddCartServiceRequest(memberId, productId);
        long cartId = cartService.add(request);
        cartService.add(request);

        List<CartResponse> carts = cartService.findCarts(memberId);
        CartResponse cart = carts.stream()
                .filter(v -> v.getId() == cartId)
                .findFirst().orElseThrow();
        assertThat(cart.getQuantity()).isEqualTo(2);
    }

    @DisplayName("등록되지 않은 회원으로 장바구니를 동록하면 예외가 발생한다.")
    @Test
    void addWithNotExistMember() {
        long memberId = 100L;
        long productId = 1L;
        AddCartServiceRequest request = new AddCartServiceRequest(memberId, productId);
        assertThatThrownBy(() -> cartService.add(request))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }


    @DisplayName("등록되지 않은 상품으로 장바구니를 동록하면 예외가 발생한다.")
    @Test
    void addWithNotExistProduct() {
        long memberId = 1L;
        long productId = 100L;
        AddCartServiceRequest request = new AddCartServiceRequest(memberId, productId);
        assertThatThrownBy(() -> cartService.add(request))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
    }

    @DisplayName("회원이 등록한 장바구니 리스트를 올바르게 가져온다.")
    @Test
    void findCarts() {
        int expected = 2;
        List<CartResponse> carts = cartService.findCarts(1L);

        assertThat(carts.size()).isEqualTo(expected);
    }

    @DisplayName("장바구니에 담긴 물품 수량을 변경한다.")
    @Test
    void updateQuantity() {
        long memberId = 4L;
        long cartId = 4L;
        int quantityToBeUpdated = 10;
        UpdateQuantityServiceRequest request = new UpdateQuantityServiceRequest(memberId, cartId, quantityToBeUpdated);

        cartService.updateQuantity(request);
        List<CartResponse> carts = cartService.findCarts(memberId);
        boolean result = carts.stream()
                .filter(v -> v.getId() == cartId)
                .anyMatch(v -> v.getQuantity() == quantityToBeUpdated);

        assertThat(result).isTrue();
    }

    @DisplayName("1개 미만의 수량으로 장바구니 업데이트시 예외가 발생한다.")
    @Test
    void updateQuantityWithUnderOneQuantity() {
        long memberId = 1L;
        long cartId = 1L;
        int quantityToBeUpdated = 0;
        UpdateQuantityServiceRequest request = new UpdateQuantityServiceRequest(memberId, cartId, quantityToBeUpdated);

        assertThatThrownBy(() -> cartService.updateQuantity(request))
                .isInstanceOf(InvalidCartQuantityException.class)
                .hasMessageContaining("상품 개수는 1개 이상이어야 합니다.");
    }

    @DisplayName("등록된 장바구니를 삭제한다.")
    @Test
    void deleteCart() {
        long cartId = 1L;
        cartService.deleteCart(1L, cartId);
        Optional<Cart> result = cartDao.findCartById(cartId);

        assertThat(result.isEmpty()).isTrue();
    }

    @DisplayName("장바구니 id가 요청한 회원의 장바구니가 아닐 경우 예외가 발생한다.")
    @Test
    void deleteCartWithNotExistMemberCart() {
        assertThatThrownBy(() -> cartService.deleteCart(1L, 3L))
                .isInstanceOf(NotInMemberCartItemException.class)
                .hasMessageContaining("장바구니 아이템이 없습니다.");
    }
}
