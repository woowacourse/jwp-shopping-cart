package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CartsTest {

    @DisplayName("List<Cart>에 해당 cartId가 있다면 true를 반환한다.")
    @Test
    void haveCartId() {
        Carts carts = new Carts(List.of(
                new Cart(1L, 1L, "이름", 5000, "www.imageuirl1.com", 50),
                new Cart(2L, 2L, "이름2", 4000, "www.imageuirl2.com", 100),
                new Cart(3L, 3L, "이름3", 1000, "www.imageuirl3.com", 10)));

        assertAll(
                () -> assertThat(carts.haveCartId(1L)).isTrue(),
                () -> assertThat(carts.haveCartId(2L)).isTrue(),
                () -> assertThat(carts.haveCartId(3L)).isTrue(),
                () -> assertThat(carts.haveCartId(4L)).isFalse()
        );
    }
}
