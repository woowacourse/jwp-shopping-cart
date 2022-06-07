package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CartTest {

    private final static Product product1 = new Product(1L, "a", 100, "https://www.naver.com");
    private final static Product product2 = new Product(2L, "b", 100, "https://www.naver.com");
    private final static Product product3 = new Product(3L, "c", 100, "https://www.naver.com");
    private final static Product product4 = new Product(4L, "c", 100, "https://www.naver.com");

    @Test
    void 체크박스의_수가_다른_경우() {
        assertThatThrownBy(() -> new Cart(List.of(product1, product2, product3), List.of(true, false, true, false), List.of(1, 2, 3)));
    }

    @Test
    void 수량의_수가_다른_경우() {
        assertThatThrownBy(() -> new Cart(List.of(product1, product2, product3), List.of(true, false, true), List.of(1, 2, 3, 4)));
    }

    @Test
    void 제품의_수가_다른_경우() {
        assertThatThrownBy(() -> new Cart(List.of(product1, product2, product3, product4), List.of(true, false, true), List.of(1, 2, 3)));
    }
}
