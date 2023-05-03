package cart.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class CartTest {
    protected static Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart(1L);
    }

    // TODO: 2023-05-02 generate 인자 null일 때 실패케이스 작성
    // TODO: 2023-05-02 generate 인자 products 중 id없는 것이 있을 때 

    @Test
    void generate_cart() {
        assertThatNoException().isThrownBy(() -> new Cart(2L));
    }

    // TODO: 2023-05-02 add 시 id가 null인 product일 경우

    @Test
    void add_product_to_cart_success() {
        //given
        Product product = new Product("pd1", "image1", 3000L);

        //when
        cart.add(product);

        //then
        assertThat(cart.getProducts()).isEqualTo(List.of(product));
    }


    @Test
    void remove_product_from_cart_success() {
        //given
        Product product = new Product(2L, "pd2", "image2", 2000L);
        cart.add(product);
        assertThat(cart.getProducts()).isNotEmpty();

        //when
        cart.remove(product);

        //then
        assertThat(cart.getProducts()).isEmpty();
    }

    // TODO: 2023-05-02 remove 시 없는 상품일 케이스 작성
}
