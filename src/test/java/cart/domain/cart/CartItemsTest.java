package cart.domain.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CartItemsTest {

    private CartItems cartItems;
    private CartItem item;

    @BeforeEach
    void setUp() {
        cartItems = new CartItems(Collections.emptyList());
        Product product = new Product("product", "testUrl", new BigDecimal("2000"));
        item = new CartItem(product);
    }

    @Test
    @DisplayName("아이템을 추가한다")
    void addTest() {
        cartItems.add(item);

        assertThat(cartItems.getItems().get(0)).isEqualTo(item);
    }

    @Test
    @DisplayName("아이템을 삭제한다")
    void removeTest() {
        cartItems.add(item);

        cartItems.remove(1);

        assertThat(cartItems.getItems().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("사이즈보다 큰 인덱스의 item을 삭제하면 예외가 발생한다.")
    void removeTestThrow() {
        assertThatThrownBy(() -> cartItems.remove(1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
