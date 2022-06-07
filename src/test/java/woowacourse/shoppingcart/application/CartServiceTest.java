package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.PutCartItemRequest;
import woowacourse.shoppingcart.exception.cart.NotInMemberCartException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CartServiceTest {

    private final CartService cartService;

    public CartServiceTest(CartService cartService) {
        this.cartService = cartService;
    }

    @DisplayName("사용자의 장바구니가 아닌 경우 예외가 발생한다.")
    @Test
    void updateCartItem() {
        assertThatThrownBy(
                () -> cartService.updateCartItem(1L, 10L, new PutCartItemRequest(10))
        ).isInstanceOf(NotInMemberCartException.class)
                .hasMessageContaining("해당 사용자의 유효한 장바구니가 아닙니다.");
    }

    @DisplayName("사용자의 장바구니가 아닌 경우 예외가 발생한다.")
    @Test
    void deleteCart() {
        assertThatThrownBy(
                () -> cartService.deleteCart(1L, 10L)
        ).isInstanceOf(NotInMemberCartException.class)
                .hasMessageContaining("해당 사용자의 유효한 장바구니가 아닙니다.");
    }
}
