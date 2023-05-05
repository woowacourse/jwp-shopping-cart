package cart.domain;

import static cart.TestFixture.IMAGE_CHICKEN;
import static cart.TestFixture.NAME_CHICKEN;
import static cart.TestFixture.PRICE_CHICKEN;
import static cart.TestFixture.PRODUCT_CHICKEN;
import static cart.TestFixture.PRODUCT_ICE_CREAM;
import static cart.TestFixture.PRODUCT_VANILLA_LATTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        this.cart = new Cart();
        cart.add(new CartItem(PRODUCT_CHICKEN));
        cart.add(new CartItem(PRODUCT_ICE_CREAM));
        cart.add(new CartItem(PRODUCT_VANILLA_LATTE));
    }

    @DisplayName("상품 추가")
    @Test
    void add() {
        assertThat(cart.getItems())
                .extracting("product.name", "product.image", "product.price")
                .contains(tuple(NAME_CHICKEN, IMAGE_CHICKEN, PRICE_CHICKEN));
    }

    @DisplayName("상품 삭제")
    @Test
    void delete() {
        cart.delete(new CartItem(PRODUCT_CHICKEN));

        assertThat(cart.getItems())
                .extracting("product.name")
                .doesNotContain(NAME_CHICKEN);
    }

    @DisplayName("상품 전체 조회")
    @Test
    void getProducts() {
        assertThat(cart.getItems())
                .containsExactlyInAnyOrder(
                        new CartItem(PRODUCT_CHICKEN),
                        new CartItem(PRODUCT_ICE_CREAM),
                        new CartItem(PRODUCT_VANILLA_LATTE)
                );
    }
}