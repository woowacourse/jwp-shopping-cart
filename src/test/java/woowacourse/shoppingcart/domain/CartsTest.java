package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.CartFixture.getFirstCart;
import static woowacourse.helper.fixture.CartFixture.getSecondCart;
import static woowacourse.helper.fixture.CartFixture.getThirdCart;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_NAME;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_PRICE;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartsTest {

    @DisplayName("존재할 경우 개수를 추가한다.")
    @Test
    void add() {
        final Carts carts = createCarts();
        carts.addOrUpdate(getFirstCart());
        final Cart cart = carts.getCartHave(new Product(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE));

        assertThat(cart.getQuantity()).isEqualTo(2);
    }

    @DisplayName("존재하지 않을 경우 추가한다.")
    @Test
    void update() {
        final Carts carts = createCarts();
        carts.addOrUpdate(getThirdCart());

        assertThat(carts.getCartHave(getThirdCart().getProduct()).isNewlyAdded()).isTrue();
    }


    public Carts createCarts() {
        List<Cart> carts = new ArrayList<>();
        carts.add(getFirstCart());
        carts.add(getSecondCart());
        return new Carts(carts);
    }

}
