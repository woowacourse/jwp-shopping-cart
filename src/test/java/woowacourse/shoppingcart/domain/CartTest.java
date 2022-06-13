package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.in;

public class CartTest {

    private final static Product product1 = new Product(1L, "a", 100, "https://www.naver.com");
    private final static Product product2 = new Product(2L, "b", 100, "https://www.naver.com");
    private final static Product product3 = new Product(3L, "c", 100, "https://www.naver.com");
    private final static Product product4 = new Product(4L, "c", 100, "https://www.naver.com");
    private final static Map<Long, Product> validCart = Map.of(1L, product1, 2L, product2, 3L, product3);
    private final static Map<Long, Product> invalidCart = Map.of(1L, product1, 2L, product2, 3L, product3, 4L, product4);

    @Test
    void 체크박스의_수가_다른_경우() {
        assertThatThrownBy(() -> new Cart(validCart, List.of(true, false, true, false), new Quantities(List.of(1, 2, 3))));
    }

    @Test
    void 수량의_수가_다른_경우() {
        assertThatThrownBy(() -> new Cart(validCart, List.of(true, false, true), new Quantities(List.of(1, 2, 3, 4))));
    }

    @Test
    void 제품의_수가_다른_경우() {
        assertThatThrownBy(() -> new Cart(invalidCart, List.of(true, false, true), new Quantities(List.of(1, 2, 3))));
    }
}
