package cart.domain;

import cart.exception.BusinessProductIdNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CartTest {
    protected static Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart(1L);
    }

    @Test
    void generate_cart_fail() {
        assertThatThrownBy(() -> new Cart(2L, null))
                .isInstanceOf(BusinessProductIdNullException.class);
    }

    @Test
    void generate_cart_success() {
        assertThatNoException().isThrownBy(() -> new Cart(2L));
    }

    @Test
    void add_product_to_cart_success() {
        //given
        Product product = new Product(2L, "pd1", "image1", 3000L);

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
}
