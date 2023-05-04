package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cart.TestFixture;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        this.cart = new Cart();
        cart.add(TestFixture.PRODUCT_CHICKEN);
        cart.add(TestFixture.PRODUCT_ICE_CREAM);
        cart.add(TestFixture.PRODUCT_VANILLA_LATTE);
    }

    @DisplayName("상품 추가")
    @Test
    void add() {
        assertThat(cart.getProducts())
                .extracting("name", "image", "price")
                .contains(tuple(TestFixture.NAME_CHICKEN, TestFixture.IMAGE_CHICKEN, TestFixture.PRICE_CHICKEN));
    }

    @DisplayName("상품 삭제")
    @Test
    void delete() {
        cart.delete(TestFixture.PRODUCT_CHICKEN_OTHER_INSTANCE);

        assertThat(cart.getProducts())
                .extracting("name")
                .doesNotContain(TestFixture.NAME_CHICKEN);
    }

    @DisplayName("상품 전체 조회")
    @Test
    void getProducts() {
        assertThat(cart.getProducts())
                .containsExactlyInAnyOrder(
                        TestFixture.PRODUCT_VANILLA_LATTE_OTHER_INSTANCE,
                        TestFixture.PRODUCT_ICE_CREAM_OTHER_INSTANCE,
                        TestFixture.PRODUCT_CHICKEN_OTHER_INSTANCE
                );
    }
}