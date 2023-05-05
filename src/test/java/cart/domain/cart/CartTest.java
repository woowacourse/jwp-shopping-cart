package cart.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.item.Item;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    Cart cart;

    @BeforeEach
    void setUp() {
        Item item = new Item(1L, "자전거", "https://image.com", 10_000);
        cart = new Cart(1L, List.of(item));
    }

    @Test
    @DisplayName("장바구니에 전달한 상품이 있는 경우 true를 반환한다.")
    void isExistsSuccessWithExistsItem() {
        Item item = new Item(1L, "자전거", "https://image.com", 10_000);

        boolean actual = cart.isExistsItem(item);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("장바구니에 전달한 상품이 없는 경우 false를 반환한다.")
    void isExistsSuccessWithNotExistsItem() {
        Item item = new Item(2L, "자전거", "https://image.com", 10_000);

        boolean actual = cart.isExistsItem(item);

        assertThat(actual).isFalse();
    }
}
