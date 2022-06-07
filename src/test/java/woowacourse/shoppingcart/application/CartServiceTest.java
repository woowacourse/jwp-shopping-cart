package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.NotInMemberCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CartServiceTest {

    private final CartService cartService;
    private final CartItemDao cartItemDao;

    public CartServiceTest(CartService cartService, CartItemDao cartItemDao) {
        this.cartService = cartService;
        this.cartItemDao = cartItemDao;
    }

    @DisplayName("올바른 데이터로 장바구니를 동록하면 장바구니 등록 ID를 반환한다.")
    @Test
    void add() {
        Long cartId = cartService.add(2L, 1L);
        assertThat(cartId).isEqualTo(1L);
    }

    @DisplayName("등록되지 않은 회원으로 장바구니를 동록하면 예외가 발생한다.")
    @Test
    void addWithNotExistMember() {
        assertThatThrownBy(() -> cartService.add(100L, 1L))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }


    @DisplayName("등록되지 않은 상품으로 장바구니를 동록하면 예외가 발생한다.")
    @Test
    void addWithNotExistProduct() {
        assertThatThrownBy(() -> cartService.add(1L, 100L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
    }

    @DisplayName("회원이 등록한 장바구니 리스트를 올바르게 가져온다.")
    @Test
    void findCarts() {
        Long cartId1 = cartService.add(1L, 1L);
        Long cartId2 = cartService.add(1L, 2L);
        Long cartId3 = cartService.add(1L, 3L);

        List<CartResponse> carts = cartService.findCarts(1L);

        assertThat(carts.size()).isEqualTo(3);
    }

    @DisplayName("등록된 장바구니를 삭제한다.")
    @Test
    void deleteCart() {
        Long cartId = cartService.add(1L, 1L);
        cartService.deleteCart(1L, cartId);
        Optional<Long> result = cartItemDao.findProductIdById(cartId);

        assertThat(result.isEmpty()).isTrue();
    }

    @DisplayName("장바구니 id가 요청한 회원의 장바구니가 아닐 경우 예외가 발생한다.")
    @Test
    void deleteCartWithNotExistMemberCart() {
        assertThatThrownBy(() -> cartService.deleteCart(1L, 2L))
                .isInstanceOf(NotInMemberCartItemException.class)
                .hasMessageContaining("장바구니 아이템이 없습니다.");
    }
}
