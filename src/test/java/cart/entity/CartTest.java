package cart.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @DisplayName("id 값을 기준으로 객체가 동일한지 판단한다.")
    @Test
    void equals_true() {
        Cart cart1 = new Cart(1L, 2L, 2L);
        Cart cart2 = new Cart(1L, 3L, 3L);

        assertThat(cart1.equals(cart2)).isTrue();
    }

    @DisplayName("id 값이 다르다면 객체가 다르다고 판단한다.")
    @Test
    void equals_false() {
        Cart cart1 = new Cart(1L, 2L, 2L);
        Cart cart2 = new Cart(2L, 2L, 2L);

        assertThat(cart1.equals(cart2)).isFalse();
    }
}
